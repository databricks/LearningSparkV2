package main.scala.chapter8

import org.apache.spark.sql.SparkSession

object SparkConfig_8_1 {

	def printConfigs(session: SparkSession) = {
		// get conf
		val mconf = session.conf.getAll
		// print them
		for (k <- mconf.keySet) { println(s"${k} -> ${mconf(k)}\n") }
	}

	def main(args: Array[String]) {
		// create a session
		val spark = SparkSession.builder
  			.config("spark.sql.shuffle.partitions", 5)
  			.master("local[*]")
				.appName("SparkConfig")
				.getOrCreate()

		printConfigs(spark)
		spark.conf.set("spark.sql.shuffle.partitions",  spark.sparkContext.defaultParallelism)
		println(" ****** Setting Shuffle Partitions to Default Parallelism")
		printConfigs(spark)
	}
}