package main.scala.chapter4

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object Example4_1 {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .appName("SparkSQLPopulationExampleApp")
      .getOrCreate()

    if (args.length <= 0) {
      println("usage Example4_1 <file path to us_population.json>")
      System.exit(1)
    }
    //get the path to the JSON file
    val jsonFile = args(0)
    //define our schema as before
    // { "zipcode" : "01001", "city" : "AGAWAM", "loc" : [ -72.622739, 42.070206 ], "pop" : 15338, "state" : "MA" }
    val schema = StructType(Array(StructField("zipcode", StringType, false),
      StructField("city", StringType, false),
      StructField("loc", ArrayType(DoubleType), false),
      StructField("pop", IntegerType, false),
      StructField("state", StringType, false)))
    // read and create a temporary view
    spark.read.schema(schema).json(jsonFile).createOrReplaceTempView("us_population_tbl")
    // example how you read data from the temporary view back into a DataFrame
    val usPopDF = spark.sql("SELECT * FROM us_population_tbl")
    // Query 1: select all cities, states, and zipcode where the population is
    // greater than 50K, and show them in descending order;
    spark.sql("SELECT state, city, zipcode, pop FROM us_population_tbl WHERE pop > 50000 ORDER BY pop DESC").show(10, false)
    // DataFrame equivalent query
    usPopDF.select("state", "city", "zipcode", "pop").where(col("pop") > 50000).orderBy(desc("pop")).show(10, false)
    // Query 2: Find the most populus cities in Calfornia
    spark.sql("SELECT  SUM(pop) as total_population, city FROM us_population_tbl WHERE state = 'CA' GROUP BY city ORDER BY total_population DESC").show(10, false)
    // Query 3: Find maximum population in the state of NY
      spark.sql("SELECT MAX(pop) AS NY_MAX_POP FROM us_population_tbl WHERE state = 'NY'").show(false)
    // Query 4: Find different density levels in each city, state, and zipcode
    spark.sql("""SELECT city, zipcode, state, pop,
                  CASE
                    WHEN pop > 90000 THEN 'High'
                    WHEN pop > 50000 AND pop < 90000 THEN  'Medium'
                    WHEN pop > 25000 AND pop < 50000 THEN  'Low'
                    ELSE 'Very Low'
                  END AS Population_Density
                  FROM us_population_tbl
                  ORDER BY pop DESC""").show(10, false)
  }
}