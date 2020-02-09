from pyspark.sql.types import *
from pyspark.sql import SparkSession
from pyspark.sql.functions import *

# define schema for our data
schema = StructType([
   StructField("Id", IntegerType(), False),
   StructField("First", StringType(), False),
   StructField("Last", StringType(), False),
   StructField("Url", StringType(), False),
   StructField("Published", StringType(), False),
   StructField("Hits", IntegerType(), False),
   StructField("Campaigns", ArrayType(StringType()), False)])

#create our data
data = [[1, "Jules", "Damji", "https://tinyurl.1", "1/4/2016", 4535, ["twitter", "LinkedIn"]],
       [2, "Brooke","Wenig","https://tinyurl.2", "5/5/2018", 8908, ["twitter", "LinkedIn"]],
       [3, "Denny", "Lee", "https://tinyurl.3","6/7/2019",7659, ["web", "twitter", "FB", "LinkedIn"]],
       [4, "Tathagata", "Das","https://tinyurl.4", "5/12/2018", 10568, ["twitter", "FB"]],
       [5, "Matei","Zaharia", "https://tinyurl.5", "5/14/2014", 40578, ["web", "twitter", "FB", "LinkedIn"]],
       [6, "Reynold", "Xin", "https://tinyurl.6", "3/2/2015", 25568, ["twitter", "LinkedIn"]]
      ]
# main program
if __name__ == "__main__":
   #create a SparkSession
   spark = (SparkSession
       .builder
       .appName("Example-3_6")
       .getOrCreate())
   # create a DataFrame using the schema defined above
   blogs_df = spark.createDataFrame(data, schema)
   # show the DataFrame; it should reflect our table above
   blogs_df.show()
   print()
   # print the schema used by Spark to process the DataFrame
   print(blogs_df.printSchema())
   # Show columns and expressions
   blogs_df.select(expr("Hits") * 2).show(2)
   blogs_df.select(col("Hits") * 2).show(2)
   blogs_df.select(expr("Hits * 2")).show(2)
   # show heavy hitters
   blogs_df.withColumn("Big Hitters", (expr("Hits > 10000"))).show()
   print(blogs_df.schema)