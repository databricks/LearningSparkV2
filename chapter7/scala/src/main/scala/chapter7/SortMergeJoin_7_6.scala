package main.scala.chapter7

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SaveMode
import scala.util.Random

object SortMergeJoin_7_6 {

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
        .appName("SortMergeJoin")
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

    usersDF.show(10)
    ordersDF.show(10)

    // do a Join
    val usersOrdersDF = ordersDF.join(usersDF, $"users_id" === $"uid")
    usersOrdersDF.show(10, false)
    usersOrdersDF.cache()
    usersOrdersDF.explain()
    // usersOrdersDF.explain("formated")
    // uncoment to view the SparkUI otherwise the program terminates and shutdowsn the UI
    // Thread.sleep(200000000)
  }
}