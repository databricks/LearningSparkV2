from pyspark.sql import SparkSession
from pyspark.ml import Pipeline
from pyspark.ml.feature import VectorAssembler, StringIndexer
from pyspark.ml.regression import RandomForestRegressor
from pyspark.ml.evaluation import RegressionEvaluator
import mlflow
import mlflow.spark
import pandas as pd
import click

spark = SparkSession.builder.appName("App").getOrCreate()

@click.command()
@click.option("--file_path", default="data/sf-airbnb-clean.parquet", type=str)
@click.option("--num_trees", default=20, type=int)
@click.option("--max_depth", default=5, type=int)
def mlflow_rf(file_path, num_trees, max_depth):
  with mlflow.start_run(run_name="random-forest") as run:
    # Create train/test split
    spark = SparkSession.builder.appName("App").getOrCreate()
    airbnbDF = spark.read.parquet(file_path)
    (trainDF, testDF) = airbnbDF.randomSplit([.8, .2], seed=42)

    # Prepare the StringIndexer and VectorAssembler
    categoricalCols = [field for (field, dataType) in trainDF.dtypes if dataType == "string"]
    indexOutputCols = [x + "Index" for x in categoricalCols]

    stringIndexer = StringIndexer(inputCols=categoricalCols, outputCols=indexOutputCols, handleInvalid="skip")

    numericCols = [field for (field, dataType) in trainDF.dtypes if ((dataType == "double") & (field != "price"))]
    assemblerInputs = indexOutputCols + numericCols
    vecAssembler = VectorAssembler(inputCols=assemblerInputs, outputCol="features")
    
    # Log params: Num Trees and Max Depth
    mlflow.log_param("num_trees", num_trees)
    mlflow.log_param("max_depth", max_depth)

    rf = RandomForestRegressor(labelCol="price",
                               maxBins=40,
                               maxDepth=max_depth,
                               numTrees=num_trees,
                               seed=42)

    pipeline = Pipeline(stages=[stringIndexer, vecAssembler, rf])

    # Log model
    pipelineModel = pipeline.fit(trainDF)
    mlflow.spark.log_model(pipelineModel, "model")

    # Log metrics: RMSE and R2
    predDF = pipelineModel.transform(testDF)
    regressionEvaluator = RegressionEvaluator(predictionCol="prediction",
                                            labelCol="price")
    rmse = regressionEvaluator.setMetricName("rmse").evaluate(predDF)
    r2 = regressionEvaluator.setMetricName("r2").evaluate(predDF)
    mlflow.log_metrics({"rmse": rmse, "r2": r2})

    # Log artifact: Feature Importance Scores
    rfModel = pipelineModel.stages[-1]
    pandasDF = (pd.DataFrame(list(zip(vecAssembler.getInputCols(),
                                    rfModel.featureImportances)),
                          columns=["feature", "importance"])
              .sort_values(by="importance", ascending=False))
    # First write to local filesystem, then tell MLflow where to find that file
    pandasDF.to_csv("/tmp/feature-importance.csv", index=False)
    mlflow.log_artifact("/tmp/feature-importance.csv")

if __name__ == "__main__":
  mlflow_rf()