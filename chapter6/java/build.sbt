 //name of the package
 // To build simply type `sbt clean package`
name := "main/java/chapter6"
//version of our package
version := "1.0"
//version of Scala
scalaVersion := "2.11.12"
// spark library dependencies
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.1",
  "org.apache.spark" %% "spark-sql"  % "2.4.1"
)