### How to build the package
 1. sbt clean package
 2. mkdir jars
 3. cp main-scala-chapter2_2.12-1.0.jar from the target/scala-2.12 directory into the jars

### How to run the M&M Example
To run Scala code for this chapter use:

 * `spark-submit --class main.scala.chapter2.MnMcount jars/main-scala-chapter2_2.12-1.0.jar data/mnm_dataset.csv

The equivalent code for Python is in the python directory, along instructions how to run it.

Have Fun
Cheers!
