# rdbms-reduce

This is a distributed reduce exercise for a scenario when resource ids are stored independently of resource details.

## Databases setup

To setup the test environment you must do the following:

1. Create a user in your mysql server called provisioning.
2. Execute the script sql/distributed_dbs.sql

## Large volume of data

You must run CreateLargeVolumeData program from com.rcosnita.experiments.rdbmsreduce package. This will create 
a large amount of data in both provisioning items and domains tables.
