# rdbms-reduce

This is a distributed reduce exercise for a scenario when resource ids are stored independently of resource details.

## Databases setup

To setup the test environment you must do the following:

1. Create a user in your mysql server called provisioning.
2. Execute the script sql/distributed_dbs.sql

## Large volume of data

You must run CreateLargeVolumeData program from com.rcosnita.experiments.rdbmsreduce package. This will create 
a large amount of data in both provisioning items and domains tables. After the application execution you will
have approx. 400.000 provisioning items and 400.000 domains in your database.


## Use cases of this distributed approach
### Test 1 - Retrieve the top 10 domains in alphabetical order

I assume I have an application that needs to retrieve the first 10 domains belonging to a specified customer.
