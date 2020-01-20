package main.java.chapter6;

import org.apache.spark.sql.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import java.io.Serializable;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.apache.spark.sql.functions.*;

public class Example6_3 {

    public static int main(String[] args) {

        SparkSession spark = SparkSession.builder().appName("Example6_8").getOrCreate();
        // Create an explicit Encoder
        Encoder<Usage> usageEncoder = Encoders.bean(Usage.class);
        Random rand = new Random();
        rand.setSeed(42);
        List<Usage> data = new ArrayList<Usage>();
        // create 1000 instances of Java Usage class
        for (int i = 0; i < 1000; i++) {
            Usage u = new Usage(i, "user-" + RandomStringUtils.randomAlphanumeric(5), rand.nextInt(1000));
            data.add(u);
        }
        // create a dataset of Usage Java typed-data
        Dataset<Usage> dsUsage = spark.createDataset(data, usageEncoder);
        dsUsage.show(10);
        // Define a filter Function
        FilterFunction<Usage> f = new FilterFunction<Usage>() {
            public boolean call(Usage u) {
                return (u.usage > 900);
            }
        };
        // use filter as Function
        dsUsage.filter(f).orderBy(col("usage").desc()).show(5);
        // define an inline MapFunction
        dsUsage.map((MapFunction<Usage, Double>) u -> {
            if (u.usage > 750)
                return u.usage * 0.15;
            else
                return u.usage * 0.50;
        }, Encoders.DOUBLE()).show(5);

        Encoder<UsageCost> usageCostsEncoder = Encoders.bean(UsageCost.class);

        dsUsage.map( (MapFunction<Usage, UsageCost>) u -> {
                double v = 0.0;
                if (u.usage > 750) v = u.usage  * 0.15; else v = u.usage  * 0.50;
                return new UsageCost(u.uid, u.uname,u.usage, v); }, usageCostsEncoder).show(5);
        return 0;
    }
}
