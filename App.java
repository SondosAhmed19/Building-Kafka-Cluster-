package com.monitoring.device;


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.time.Duration;


public class MainClass {
    //configration ServerConsumer 
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "groupA";
    private static final String TOPIC_NAME = "test-topic4";

    public static void main(String[] args) {
         ScheduledThreadPoolExecutor th = new ScheduledThreadPoolExecutor(10);
        
        try {
            Balancer b = new Balancer("test-topic3");
            th.scheduleAtFixedRate(b, 0, 1, TimeUnit.SECONDS);
            for (int i=0;i<10;i++){
                Server s = new Server(i,"test-topic4");
                th.scheduleAtFixedRate(s, 0, 100, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e){
            e.printStackTrace(System.out);

            
        // Set Kafka bootstrap servers
        String bootstrapServers = "localhost:9092";

        // Set properties for AdminClient
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Create AdminClient
        try (AdminClient adminClient = AdminClient.create(properties)) {
            // Create a new topic
            String topicName = "test-topic4";
            int numPartitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);

            // Create the topic
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();


            System.out.println("Topic c created successfully: " + topicName);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to create topic: " + e.getMessage());
        }
    }
       // Create Kafka  ServerConsumer runnable
        ServerConsumerRunnable consumerRunnable = new ServerConsumerRunnable("127.0.0.1:9092", "groupA", "test-topic4");

         // Subscribe to the topic(s) from which you want to consume metrics
        consumer.subscribe(Collections.singletonList("test-topic4"));


        // Create and start a new thread for the consumer
        Thread consumerThread = new Thread(consumerRunnable);
        consumerThread.start();

       
        // Start consuming and processing metrics
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            
            // Process the received metrics records
            for (ConsumerRecord<String, String> record : records) {
                String metric = record.value();
                
                // Insert the metric into the relational database
                insertMetricIntoDatabase(metric);
            }
        }
    }
}



