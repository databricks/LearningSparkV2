// In Scala
package main.scala.chapter3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object schema_ex {
    def main(args: Array[String]) {
        
        // Create the SparkSession
        val spark = SparkSession.builder.appName("Chapter3 - Schema Example").getOrCreate()

        if (args.length <= 0) {
            println("usage schema_ex <file path to blogs.json>")
            System.exit(1)
        }

        // Get the path to JSON file
        val jsonFile = args(0)
        // Define our schema progammatically
        val schema = StructType(Array(StructField("Id", IntegerType, false),
                        StructField("First", StringType, false),
                        StructField("Last", StringType, false),
                        StructField("Url", StringType, false),
                        StructField("Published", StringType, false),
                        StructField("Hits", IntegerType, false),
                        StructField("Campaigns", ArrayType(StringType), false)))
    
        // Create a DataFrane by reading form the JSON file
        // with a predefined schema
        val blogsDF = spark.read.schema(schema).json(jsonFile)
        // Show the DataFrame schema as output
        blogsDF.show(false)

        // Print the schema
        println(blogsDF.printSchema)
        println(blogsDF.schema)
    }
}