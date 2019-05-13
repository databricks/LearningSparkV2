from pyspark.sql.types import *
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, desc
import sys

# main program
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: Example4_1.py <population.json", file=sys.stderr)
        sys.exit(-1)
        # create a SparkSession

    spark = (SparkSession
             .builder
             .appName("SparkSQLPopulationExampleApp")
             .getOrCreate())
    # get the path to the JSON file
    json_file = sys.argv[1]
    # define schema for our zipcode data
    # {"zipcode": "01001", "city": "AGAWAM", "loc": [-72.622739, 42.070206], "pop": 15338, "state": "MA"}
    schema = StructType([
            StructField("zipcode", StringType(), False),
            StructField("city", StringType(), False),
            StructField("loc", ArrayType(StringType()), False),
            StructField("pop", IntegerType(), False),
            StructField("state", StringType(), False)])
    # read the data and create a temporary view in memory
    spark.read.schema(schema).json(json_file).createOrReplaceTempView("us_population_tbl")
    # example how you read data from the temporary view back into a DataFrame
    us_pop_df = spark.sql("SELECT * FROM us_population_tbl")
    # Query 1: select all cities, states, and zipcode where the population is
    # greater than 50K, and show them in descending order;
    spark.sql("SELECT state, city, zipcode, pop FROM us_population_tbl WHERE pop > 50000 ORDER BY pop DESC").show(10, truncate=False)
    # DataFrame equivalent query
    us_pop_df.select("state", "city", "zipcode", "pop").where(col("pop") > 50000).orderBy(desc("pop")).show(10, truncate=False)
    # Query 2: Find the most populus cities in Calfornia
    spark.sql("SELECT  SUM(pop) as total_population, city FROM us_population_tbl WHERE state = 'CA' GROUP BY city ORDER BY total_population DESC").show(10, truncate=False)
    # Query 3: Find maximum population in the state of NY
    spark.sql("SELECT MAX(pop) AS NY_MAX_POP FROM us_population_tbl WHERE state = 'NY'").show()
    # Query 4: Find different density levels in each city, state, and zipcode
    spark.sql("""SELECT city, zipcode, state, pop,
                  CASE
                    WHEN pop > 90000 THEN 'High'
                    WHEN pop > 50000 AND pop < 90000 THEN  'Medium'
                    WHEN pop > 25000 AND pop < 50000 THEN  'Low'
                    ELSE 'Very Low'
                  END AS Population_Density
                  FROM us_population_tbl
                  ORDER BY pop DESC""").show(10, truncate=False)


