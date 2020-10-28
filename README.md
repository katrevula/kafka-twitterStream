# kafka

Custom Kafka Producer and Consumer apps.

Install Apache Kafka

Run below Commands to start Kafka and Zookeeper

Start Zookeeper:
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties

Start Kafka server:
kafka-server-start /usr/local/etc/kafka/server.properties


Commands to Run Project: Use different terminal tabs

Build Project:
 
mvn clean compile assembly:single

Run Producer:
java -cp target/kafka-twitterStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar edu/nwmissouri/dv/twitterStreaming/CustomProducer

Run Consumer:
java -cp target/kafka-twitterStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar edu/nwmissouri/dv/twitterStreaming/CustomConsumer
