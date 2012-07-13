# rdbms-reduce

This is a distributed reduce exercise for a scenario when resource ids are stored independently of resource details.

## Databases setup

To setup the test environment you must do the following:

1. Create a user in your mysql server called provisioning.
2. Execute the script sql/distributed_dbs.sql

## Large volume of data

You must run CreateLargeVolumeData program from com.rcosnita.experiments.rdbmsreduce package.

Example: java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.LargeVolumeDataCreator 1000000 40 100

This will generate 1000000 provisioning items evenly distributed among 40 consecutive accounts, first account being 100.


## Use cases of this distributed approach
### Test 1 - Retrieve the top n domains in alphabetical order

I assume I have an application that needs to retrieve the first n domains belonging to a specified customer. You can easy see this
use case by running: java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.examples.TopDomains <account_id> <number_of_domains>

### Test 2 - Retrieve the top n domains matching a given filter

I assume I have an application that needs to retrieve the first n domains belonging to a specified customer and belonging to 
co.uk tld. You can easy see this use case running by accessing: 
java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.examples.TopDomainsWithFiltering <account_id> <number_of_domains> <tld_pattern>

For account 100, top 50 domains and '.co.uk' tld it took under < 600ms against 400.000 prov ids and 13333 domains.
