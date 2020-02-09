// scalastyle:off println

package main.scala.chapter2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Usage: MnMcount <mnm_file_dataset>
  */
object MnMcount {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("MnMCount")
      .getOrCreate()

    if (args.length < 1) {
      print("Usage: MnMcount <mnm_file_dataset>")
      sys.exit(1)
    }
    // get the M&M data set file name
    val mnm_file = args(0)
    // read the file into a Spark DataFrame
    val mnm_df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(mnm_file)

    // aggregate count of all colors and groupBy state and color
    // orderBy descending order
    val count_mnm_df = mnm_df.select("State", "Color", "Count")
        .groupBy("State", "Color")
        .agg(count("Count")
        .alias("Total"))
        .orderBy(desc("Total"))
    // show all the resulting aggregation for all the dates and colors
    count_mnm_df.show(60)
    println(s"Total Rows = {count_mnm_df.count()}")
    println()

    // create a temporary table
    mnm_df.createOrReplaceTempView("MNM_TABLE_NAME")
    val count_mnm_sql_df = spark.sql("""SELECT State, Color, Count, sum(Count) AS Total
      FROM MNM_TABLE_NAME
      GROUP BY State, Color, Count
      ORDER BY Total DESC""")
    count_mnm_df.show(60)

    // find the aggregate count for California by filtering
    val ca_count_mnm_df = mnm_df.select("*")
      .where(col("State") === "CA")
      .groupBy("State", "Color")
      .agg(count("Count")
      .alias("Total"))
      .orderBy(desc("Total"))
    // show the resulting aggregation for California
    ca_count_mnm_df.show(10)
  }
}
// scalastyle:on println
