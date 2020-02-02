package main.scala.chapter8

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SaveMode
import scala.util.Random

object SortMergeJoinBucketed_8_6 {

  // curried function to benchmark any code or function
  def benchmark(name: String)(f: => Unit) {
    val startTime = System.nanoTime
    f
    val endTime = System.nanoTime
    println(s"Time taken in $name: " + (endTime - startTime).toDouble / 1000000000 + " seconds")
  }

  // main class setting the configs
  def main (args: Array[String] ) {

    val spark = SparkSession.builder
        .appName("SortMergeJoinBucketed")
        .config("spark.sql.codegen.wholeStage", true)
        .config("spark.sql.join.preferSortMergeJoin", true)
        .config("spark.sql.autoBroadcastJoinThreshold", -1)
        .config("spark.sql.defaultSizeInBytes", 100000)
        .config("spark.sql.shuffle.partitions", 16)
        .getOrCreate ()

    import spark.implicits._

    var states = scala.collection.mutable.Map[Int, String]()
    var items = scala.collection.mutable.Map[Int, String]()
    val rnd = new scala.util.Random(42)

    // initialize states and items purchased
    states += (0 -> "AZ", 1 -> "CO", 2-> "CA", 3-> "TX", 4 -> "NY", 5-> "MI")
    items += (0 -> "SKU-0", 1 -> "SKU-1", 2-> "SKU-2", 3-> "SKU-3", 4 -> "SKU-4", 5-> "SKU-5")
    // create dataframes
    val usersDF = (0 to 100000).map(id => (id, s"user_${id}", s"user_${id}@databricks.com", states(rnd.nextInt(5))))
          .toDF("uid", "login", "email", "user_state")
    val ordersDF = (0 to 100000).map(r => (r, r, rnd.nextInt(100000), 10 * r* 0.2d, states(rnd.nextInt(5)), items(rnd.nextInt(5))))
          .toDF("transaction_id", "quantity", "users_id", "amount", "state", "items")

    // let's create five buckets, each DataFrame for their respective columns
    // create bucket and table for uid
    spark.sql("DROP TABLE IF EXISTS UsersTbl")
    usersDF.orderBy(asc("uid"))
      .write.format("parquet")
      .mode(SaveMode.Overwrite)
      .bucketBy(5, "uid")
      .saveAsTable("UsersTbl")
    // create bucket and table for users_id
    spark.sql("DROP TABLE IF EXISTS OrdersTbl")
    ordersDF.orderBy(asc("users_id"))
      .write.format("parquet")
      .bucketBy(5, "users_id")
      .mode(SaveMode.Overwrite)
      .saveAsTable("OrdersTbl")
    // cache tables
    spark.sql("CACHE TABLE UsersTbl")
    spark.sql("CACHE TABLE OrdersTbl")
    // read data back in
    val usersBucketDF = spark.table("UsersTbl")
    val ordersBucketDF = spark.table("OrdersTbl")
    // Now do the join on the bucketed DataFrames
    val joinUsersOrdersBucketDF = ordersBucketDF.join(usersBucketDF, $"users_id" === $"uid")
    joinUsersOrdersBucketDF.show(false)
    joinUsersOrdersBucketDF.explain(true)

    // to view the SparkUI otherwise the program terminates and shutdowsn the UI
    Thread.sleep(200000000)
  }
}