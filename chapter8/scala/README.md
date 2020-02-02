### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp main-scala-chapter4_2.12-1.0.jar from the target/ directory into the jars/

### How to run the example
To run Scala code for this chapter use:

 * `spark-submit --class main.scala.chapter8.SparkConfig_8_1 jars/main-scala-chapter8_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter8.Partitions_8_2 jars/main-scala-chapter8_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter8.MapAndMapPartitions_8_3 jars/main-scala-chapter8_2.12-1.0.jar`´
 * `spark-submit --class main.scala.chapter8.CachingData_8_5 jars/main-scala-chapter8_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter8.SortMergeJoin_8_6 jars/main-scala-chapter8_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter8.SortMergeJoinBucketed_8_6 jars/main-scala-chapter8_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter8.SortMergeJoin_8_6 jars/main-scala-chapter8_2.12-1.0.jar`

Have Fun
Cheers!
~
