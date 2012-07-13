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
### Test 1 - Retrieve the top n domains in alphabetical order

I assume I have an application that needs to retrieve the first n domains belonging to a specified customer. You can easy see this
use case by running: java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.examples.TopDomains <account_id> <number_of_domains>
