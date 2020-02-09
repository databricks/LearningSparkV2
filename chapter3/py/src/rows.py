from pyspark.sql import Row
from pyspark.sql.types import *
from pyspark.sql import SparkSession

spark = (SparkSession
        .builder
	.appName("Authors")
	.getOrCreate())

schema = StructType([
	StructField("Author" , StringType(), False), 
	StructField("State", StringType(), False)])

rows = [Row("Matei Zaharia", "CA"), Row("Reynold Xin", "CA")]
authors_df = spark.createDataFrame(rows, schema)
authors_df.show()
