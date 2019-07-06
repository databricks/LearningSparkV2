package main.java.chapter6;

import org.apache.spark.sql.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class PersonExample {

    public static int main(String[] args) {

        SparkSession spark = SparkSession.builder().appName("Person").getOrCreate();
        // Create an explicit Encoder
        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        // create single instances of Java Person class
        List<Person> personList  = new ArrayList<Person>(1);
        personList.add(new Person("Jules Damji", 60));
        Dataset<Person> personDS = spark.createDataset(personList, personEncoder);

        System.out.println("*** here is the schema inferred from the bean");
        personDS.printSchema();

        personDS.show();

        // test just String
        List<String> data = Arrays.asList("hello", "world");
        Dataset<String> ds = spark.createDataset(data, Encoders.STRING());
        ds.show();
        return 0;
    }
}

