### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp target/scala-2.12/main-scala-chapter2_2.12-1.0.jar jars/

### How to run the M&M Example
To run the Scala code for this chapter use:

 * `spark-submit --class main.scala.chapter2.MnMcount jars/main-scala-chapter2_2.12-1.0.jar data/mnm_dataset.csv`
