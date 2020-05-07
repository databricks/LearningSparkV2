### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp main-scala-chapter4_2.12-1.0.jar from the target/scala-2.12 directory into the jars/
### How to run the code

To run the Scala code for this chapter:

* `spark-submit --class main.scala.chapter4.Example4_1 jars/main-scala-chapter4_2.12-1.0.jar ../data/us_population.json`
* `spark-submit --class main.scala.chapter4.SparkTables jars/main-scala-chapter4_2.12-1.0.jar ../data/us_population.json`
