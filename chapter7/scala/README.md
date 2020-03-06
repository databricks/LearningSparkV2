### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp main-scala-chapter7_2.12-1.0.jar from the target/scala-2.12 directory into the jars/

### How to run the example
To run Scala code for this chapter use:

 * `spark-submit --class main.scala.chapter7.SparkConfig_7_1 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.Partitions_7_2 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.MapAndMapPartitions_7_3 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.CachingData_7_5 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.SortMergeJoin_7_6 jars/main-scala-chapter7_2.12-1.0.jar`
 * `spark-submit --class main.scala.chapter7.SortMergeJoinBucketed_7_6 jars/main-scala-chapter7_2.12-1.0.jar`

Have Fun
Cheers!

