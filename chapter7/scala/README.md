### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp target/scala-2.12/main-scala-chapter7_2.12-1.0.jar jars/
 
** Note**:" Some of these standalone applications such as `SparkConfig_7_1`, `Partitions_7_2`, `
MapAndMapPartitions_7_3`, `MapAndMapPartitions_7_3`, and `CachingData_7_5` are extras; they are not part of 
the chapter 7 discourse but here for exploration.

### How to run the example
To run the Scala code for this chapter use: 

 * `spark-submit --class main.scala.chapter7.SparkConfig_7_1 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --conf spark.sql.shuffle.partitions=5 --conf "spark.executor.memory=2g" --class main.scala.chapter7.SparkConfig_7_1 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.Partitions_7_2 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.MapAndMapPartitions_7_3 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.CachingData_7_5 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.SortMergeJoin_7_6 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.SortMergeJoinBucketed_7_6 jars/main-scala-chapter7_2.12-1.0.jar`

Have Fun
Cheers!

