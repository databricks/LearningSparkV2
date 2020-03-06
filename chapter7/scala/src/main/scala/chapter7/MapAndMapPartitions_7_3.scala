package main.scala.chapter7

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import scala.math.sqrt
import java.io.FileWriter

object MapAndMapPartitions_7_3 {

  // simulate a connection to a FS
  def getConnection (f: String): FileWriter = {
    new FileWriter(f, true)
  }

  // use function with map()
  def func(v: Long) = {
    // make a connection to DB
    val conn = getConnection("/tmp/sqrt.txt")
    val sr = sqrt(v)
    // write value out to DB
    conn.write(sr.toString())
    conn.write(System.lineSeparator())
    conn.close()
    sr
  }
  // use function for mapPartition
  def funcMapPartions(conn:FileWriter, v: Long) = {
    val sr = sqrt(v)
    conn.write(sr.toString())
    conn.write(System.lineSeparator())
    sr
  }

  // curried function to benchmark any code or function
  def benchmark(name: String)(f: => Unit) {
    val startTime = System.nanoTime
    f
    val endTime = System.nanoTime
    println(s"Time taken in $name: " + (endTime - startTime).toDouble / 1000000000 + " seconds")
  }


  def main (args: Array[String] ) {

    val spark = SparkSession
    .builder
    .appName("MapAndMapPartitions")
    .getOrCreate ()

    import spark.implicits._

    val df = spark.range(1 * 10000000).toDF("id").withColumn("square", $"id" * $"id").repartition(16)
    df.show(5)

    // Benchmark Map function
    benchmark("map function") {
      df.map(r => (func(r.getLong(1)))).show(10)
    }

    // Benchmark MapPartiton function
    benchmark("mapPartion function") {

      val newDF = df.mapPartitions(iterator => {
        val conn = getConnection("/tmp/sqrt.txt")
        val result = iterator.map(data=>{funcMapPartions(conn, data.getLong(1))}).toList
        conn.close()
        result.iterator
      }
      ).toDF().show(10)
    }
  }
}

