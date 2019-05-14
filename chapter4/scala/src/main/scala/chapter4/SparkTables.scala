package main.scala.chapter4

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

object SparkTables {

  def listTables(ss: SparkSession, tname: String): Unit = {
    ss.catalog.listDatabases().collect().foreach(println(_))
    System.out.println()
    ss.catalog.listTables().collect().foreach(println(_))
    System.out.println()
    ss.catalog.listColumns(tname).collect().foreach(println(_))
  }

  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .appName("SparkSQLTablesApp")
      .enableHiveSupport()
      .getOrCreate()

    if (args.length <= 0) {
      println("usage Example4_1 <file path to us_population.json>")
      System.exit(1)
    }
    //get the path to the JSON file
    val json_file = args(0)

    // create database and managed tables
    spark.sql("DROP DATABASE IF EXISTS learn_spark_v2 CASCADE")
    spark.sql("CREATE DATABASE learn_spark_v2")
    spark.sql("USE learn_spark_v2")
    spark.sql("CREATE TABLE us_population_tbl (zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT)")

    //use Catalog to list the database and tables
    System.out.println("Listing Catalog Information")
    listTables(spark, "us_population_tbl")
    // drop the database and create unmanaged tables
    spark.sql("DROP DATABASE IF EXISTS learn_spark_v2 CASCADE")
    spark.sql("CREATE DATABASE learn_spark_v2")
    spark.sql("USE learn_spark_v2")
    spark.sql("CREATE TABLE us_population_tbl(zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT) USING org.apache.spark.sql.json OPTIONS(path '../../data/us_population.json')")

    /**
      * It seems like you cannot bind a variable as an argument in OPTION statement in SQL DDL
      * spark.sql("CREATE TABLE us_population_tbl(zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT) USING org.apache.spark.sql.json OPTIONS(path json_file)")
      **/
    listTables(spark, "us_population_tbl")
  }
}