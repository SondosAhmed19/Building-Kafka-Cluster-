## Kafka_Metrics_And_Logs_Pipeline
**overview**
Design a Multi Node Kafka Cluster have two topics. One topic for the agents of the 10 servers which monitor resources consumption, the other topic for the agent of the load balancer which monitor logs. 

After that, build a consumers for the metrics that should send them to a relational database. For the logs, write a spark application that consumes them and calculate a moving windows count of every operation 

 **The goal is to process and store metrics and logs effectively using Kafka, a relational database, and Hadoop. The system architecture includes:**
 
10 Servers: Each with an agent to send resource consumption metrics.

Load Balancer: Equipped with an agent to send log data.

Kafka Cluster: To handle the incoming data.

Relational Database(MySQL): For storing metrics.

Spark Application: To process logs and calculate moving window counts.


## How to Get Satrted?

