## How to build the package
1. sbt clean package
2. mkdir jars
3. cp main-java-chapter6_2.12-1.0.jar from the target/scala-2.12 directory into the jars

## How to run the Example

`spark-submit --class main.java.chapter6.Example6_3 jars/main-java-chapter6_2.12-1.0.jar`
