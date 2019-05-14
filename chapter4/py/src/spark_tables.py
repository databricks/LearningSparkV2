from pyspark.sql import SparkSession
import sys
import os.path


def list_tables(ss, tname):
    """
    Use the Catalog API interface to list some databases, tabels, columns
    :param ss: SparkSession
    :return:  none
    """
    print(ss.catalog.listDatabases())
    print()
    print(ss.catalog.listTables())
    print()
    print(ss.catalog.listColumns(tname))

# main program
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: spark_tables.py <us_population.json>", file=sys.stderr)
        sys.exit(-1)
        # create a SparkSession

    spark = (SparkSession
             .builder
             .appName("SparkSQLTablesApp")
             .enableHiveSupport()
             .getOrCreate())
    # get the path to the JSON file
    json_file = os.path.join(os.getcwd(), sys.argv[1])

    if __name__ == '__main__':
        # create database and managed tables
        spark.sql("DROP DATABASE IF EXISTS learn_spark_v2 CASCADE")
        spark.sql("CREATE DATABASE learn_spark_v2")
        spark.sql("USE learn_spark_v2")
        spark.sql("CREATE TABLE us_population_tbl (zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT)")

        # use Catalog to list the database and tables
        list_tables(spark)
        # drop the database and create unmanaged tables
        spark.sql("DROP DATABASE IF EXISTS learn_spark_v2 CASCADE")
        spark.sql("CREATE DATABASE learn_spark_v2")
        spark.sql("USE learn_spark_v2")
        spark.sql("CREATE TABLE us_population_tbl(zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT) USING org.apache.spark.sql.json OPTIONS(path '../../data/us_population.json')")
        """
        It seems like you cannot bind variables as argument in OPTIONs statement in SQL DDL
        spark.sql("CREATE TABLE us_population_tbl(zipcode STRING, city STRING, loc ARRAY<DOUBLE>, pop INT) USING org.apache.spark.sql.json OPTIONS(path json_file)")
        """
        list_tables(spark)






