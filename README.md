# rdbms-reduce

This is a distributed reduce exercise for a scenario when resource ids are stored independently of resource details.

## Databases setup

To setup the test environment you must do the following:

1. Create a user in your mysql server called provisioning.
2. Execute the script sql/distributed_dbs.sql (if you want unindexed database).
3. Execute the script sql/distributed_dbs_indexed.sql (if you want indexed database - closer to production environment).

## Large volume of data

You must run CreateLargeVolumeData program from com.rcosnita.experiments.rdbmsreduce package.

Example: java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.LargeVolumeDataCreator 1000000 40 100

This will generate 1000000 provisioning items evenly distributed among 40 consecutive accounts, first account being 100.


## Use cases of this distributed approach
### Test 1 - Retrieve the top n domains in alphabetical order

I assume I have an application that needs to retrieve the first n domains belonging to a specified customer. You can easy see this
use case by running: java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.examples.TopDomains <account_id> <number_of_domains> <first_page - 0 based>

### Test 2 - Retrieve the top n domains matching a given filter

I assume I have an application that needs to retrieve the first n domains belonging to a specified customer and belonging to 
co.uk tld. You can easy see this use case running by accessing: 
java -cp rdbms-reduce.jar com.rcosnita.experiments.rdbmsreduce.examples.TopDomainsWithFiltering <account_id> <number_of_domains> <tld_pattern> <first_page - 0 based>

For account 100, top 50 domains and '.co.uk' tld it took under < 600ms against 400.000 prov ids and 13333 domains.

# Database optimization

Both use cases forementioned are working relatively well for tables which are not partitioned. In some cases you will have a large number
of records and then the use cases will degrade in performance. For these scenarios you have a fairly easy solution at your disposal: partitioning.

1. For provisioning ids the table can be easily partitioned by account_id. Almost all use cases for the distributed configuration will
start from an account id.
2. For store1_products.domains table the partioning will be done by prov_ids. This will facilitate multithreaded retrieval of looked up provisioning
identifiers.

You can easily test this but dropping provisioning and store1_products schemas and recreating them by using sql/distributed_dbs_partitioned.sql.
When the scripts finish the execution you will have 2.000.000 provisioning items and 2.000.000 domains in place. Rerun the use cases and
you will be able to see great improvements. 

On my machine, for the top 50 domains ordered alphabetically I obtained a total time of ~670 ms. For the top 50 domains ordered alphabetically
and having tld = '.co.uk' I obtained ~575ms.


