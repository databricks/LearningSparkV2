package main.scala.chapter7

import org.apache.spark.sql.SparkSession

object Partitions_7_2 {

	def printConfigs(session: SparkSession) = {
		// get conf
		val mconf = session.conf.getAll
		// print them
		for (k <- mconf.keySet) { println(s"${k} -> ${mconf(k)}\n") }
	}

	def main(args: Array[String]) {
		// create a session
		val spark = SparkSession.builder
  			.master("local[*]")
				.appName("Partitions")
				.getOrCreate()

		printConfigs(spark)
		val numDF = spark.range(1000L * 1000 * 1000).repartition(16)
		println(s"****** Number of Partiions in DataFrame: ${numDF.rdd.getNumPartitions}")
		spark.conf.set("spark.sql.shuffle.partitions",  spark.sparkContext.defaultParallelism)
		println("****** Setting Shuffle Partitions to Default Parallelism")
		printConfigs(spark)
	}
}