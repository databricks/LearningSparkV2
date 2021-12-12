## Running Notebooks

We have provided Python and Scala notebooks in the `LearningSparkv2.dbc` file. Follow the steps below to import these notebooks into Databricks. Most of this code should also run outside the Databricks environment, such as Jupyter notebooks, but you will need to create your own `SparkSession`. 

* Step 1: Register for a free [Databricks Community Edition](https://www.databricks.com/try-databricks) account
* Step 2: Login to Community Edition
* Step 3: Create a cluster with Databricks Runtime 9.1 ML LTS. The Databricks runtime for ML pre-installs many common ML libraries, some of which are used in chapter 11 (e.g. MLflow, XGBoost, etc.)
* Step 4: Follow the steps here to [import a notebook](https://docs.databricks.com/notebooks/notebooks-manage.html#import-a-notebook) to import this dbc file: `https://github.com/databricks/LearningSparkV2/blob/master/notebooks/LearningSparkv2.dbc`
