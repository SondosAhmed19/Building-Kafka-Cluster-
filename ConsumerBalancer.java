import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ServerConsumerRunnable implements Runnable {
    private final String bootstrapServers;
    private final String groupId;
    private final String topicName;

    public BalancerConsumerRunnable(String bootstrapServers, String groupId, String topicName) {
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.topicName = topicName;
    }

    @Override
    public void run() {
        // Create consumer configuration
        Properties consumerProps = KafkaConsumerConfig.createConsumerConfig(bootstrapServers, groupId);

        // Create Kafka consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        // Subscribe to topic(s)
        consumer.subscribe(Collections.singletonList(topicName));

        // Start consuming messages
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            // Process the received records
            // ...

            // Add your processing logic here
        }
    }
    
}