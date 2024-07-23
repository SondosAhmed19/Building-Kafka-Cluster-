import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {
            private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
            private static final String USERNAME = "root";
            private static final String PASSWORD = "password";

            public static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            }
        }
        public class MetricProcessor {
            public static void insertMetric(Connection connection, String metric) throws SQLException {
            String sql = "INSERT INTO metrics (value) VALUES (?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, metric);
                    statement.executeUpdate();
                }
           }
        }


public class ServerConsumerRunnable implements Runnable {
    private String bootstrapServers;
    private String groupId;
    private String topicName;

    public ServerConsumerRunnable(String bootstrapServers, String groupId, String topicName) {
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.topicName = topicName;
         
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }


    @Override
    public void run() {
        // Create consumer configuration
        Properties consumerProps = KafkaConsumerConfig.createConsumerConfig(bootstrapServers, groupId);

        // Create Kafka consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        // Subscribe to topic(s)
        consumer.subscribe(Collections.singletonList(topicName));

        // Start consuming matrics
         while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            
            // Process the received metrics records
            for (ConsumerRecord<String, String> record : records) {
                String metric = record.value();
                
                // Insert the metric into the relational database
                insertMetric(metric);
          }
        }
    }

   
/*     private static void insertMetricIntoDatabase(String metric) {
             Connection connection = null;
             PreparedStatement statement = null;

    try {
        // Establish a connection to the database
        connection = DriverManager.getConnection("jdbc:your-database-url", "username", "password");

        // Prepare the insert statement
        String insertQuery = "INSERT INTO metrics_table (metric) VALUES (?)";
        statement = connection.prepareStatement(insertQuery);
        statement.setString(1, metric);

        // Execute the insert statement
        statement.executeUpdate();

        System.out.println("Metric inserted into the database: " + metric);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close the statement and connection
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    
    
     
