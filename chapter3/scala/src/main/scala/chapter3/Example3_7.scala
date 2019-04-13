package main.scala.chapter3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object Example3_7 {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .appName("Example-3_7")
      .getOrCreate()

    if (args.length <= 0) {
      println("usage Example3_7 <file path to blogs.json")
      System.exit(1)
    }
    //get the path to the JSON file
    val jsonFile = args(0)
    //define our schema as before
    val schema = StructType(Array(StructField("Id", IntegerType, false),
      StructField("First", StringType, false),
      StructField("Last", StringType, false),
      StructField("Url", StringType, false),
      StructField("Published", StringType, false),
      StructField("Hits", IntegerType, false),
      StructField("Campaigns", ArrayType(StringType), false)))

    //Create a DataFrame by reading from the JSON file a predefined Schema
    val blogs_df = spark.read.schema(schema).json(jsonFile)
    print()
    //show the DataFrame schema as output
    blogs_df.show(truncate = false)
    // print the schemas
    print(blogs_df.printSchema)
    print(blogs_df.schema)
  }
}