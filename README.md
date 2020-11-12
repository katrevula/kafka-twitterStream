# Streaming Tweets and Visluazing using kafka and Tableau

## Prerequisites
* Apache Kafka Installed
* Java 8 installed
* Twiiter App

## Steps to create Twitter App
* Create a Twiiter Developer Account
* Go to the developer portal
* Select on the projects & Apps ta on the side naviation bar
* Click on the create app button under the Standlone Apps 
* Fill the details os the app and create an app
* After successfully creating the app, go to keys and tokens tab and generate keys
* Copy and paste teh keys for future use

#### Example keys & Tokens

```
oauth.consumerKey=6cL......
oauth.consumerSecret=21KGga.....
oauth.accessToken=32.....
oauth.accessTokenSecret=qkOZ.....
```

##### To access the tweets from twitter API, copy and paste the above keys and tokens to the twiiter4j.properties file under resources folder

### Run below Commands to start Kafka and Zookeeper

## Start Zookeeper:
```
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
```

## Start Kafka server:
```
kafka-server-start /usr/local/etc/kafka/server.properties
```

### Commands to Run Project: Use different terminal tabs

## Build Project:
 
```
mvn clean compile assembly:single
```

## Run Producer:
``` 
java -cp target/kafka-twitterStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar edu/nwmissouri/dv/twitterStreaming/CustomProducer 
```

## Run Consumer:
```
java -cp target/kafka-twitterStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar edu/nwmissouri/dv/twitterStreaming/CustomConsumer
```


## Rockset Run command:

```
./kafka_2.11-2.3.0/bin/connect-standalone.sh ./connect-standalone.properties ./connect-rockset-sink.properties 
```
