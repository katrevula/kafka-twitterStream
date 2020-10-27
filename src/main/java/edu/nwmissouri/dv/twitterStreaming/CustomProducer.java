package edu.nwmissouri.dv.twitterStreaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Custom Producer using Kafka for messaging.
 * Reads properties from the run.properties file in
 * src/main/resources.
 */
public class CustomProducer {
    private static FileInputStream runStream = null;
    private static Properties runProperties = new Properties();

    public static void main(String[] argv) throws Exception {
        // Create an input stream for the run properties ................
        String runFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "run.properties";
        System.out.println("Reading config from " + runFile);
        runStream = new FileInputStream(runFile);

        // Load properties and display
        runProperties.load(runStream);
        System.out.println("Run properties.................");
        System.out.println("BOOTSTRAP_SERVERS_CONFIG =      " + runProperties.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        System.out.println("KEY_SERIALIZER_CLASS_CONFIG =   " + runProperties.getProperty("KEY_SERIALIZER_CLASS_CONFIG"));
        System.out.println("VALUE_SERIALIZER_CLASS_CONFIG = " + runProperties.getProperty("VALUE_SERIALIZER_CLASS_CONFIG"));
        System.out.println("TOPIC =                         " + runProperties.getProperty("TOPIC"));
        System.out.println("TWITTER_USER =                  " + runProperties.getProperty("TWITTER_USER"));
        System.out.println("DELAY_MS =                      " + runProperties.getProperty("DELAY_MS"));

        String topicName = runProperties.getProperty("TOPIC");
        String user = runProperties.getProperty("TWITTER_USER");
        int delay_ms = Integer.parseInt(runProperties.getProperty("DELAY_MS"));

        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                runProperties.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                runProperties.getProperty("KEY_SERIALIZER_CLASS_CONFIG"));
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                runProperties.getProperty("VALUE_SERIALIZER_CLASS_CONFIG"));
        org.apache.kafka.clients.producer.Producer<String, CustomObject> producer = new KafkaProducer<String, CustomObject>(configProperties);
//    org.apache.kafka.clients.producer.Producer<String, Integer> producer = new KafkaProducer<String, Integer>(configProperties);
//    org.apache.kafka.clients.producer.Producer<String, JSONObject> producer = new KafkaProducer<String, JSONObject>(configProperties);

//      Producer<String,CustomObject> kafkaProducer = new KafkaProducer<String,CustomObject>(props, new StringSerializer(),new KafkaJsonSerializer());

        System.out.println("==========================================");
        System.out.println("You must start a consumer to see messages.");
        System.out.println("==========================================");
        System.out.println("\nStarting custom producer..............\n");

        List<Integer> woeids = method();


        List<TrendingNow> trendingNowList = method2(1);



//        for (Integer woeid : woeids) {
//            method2(woeid);
//        }


        Twitter twitter = getTwitterinstance();
        int pageno = 1;
        int i = 1;
        List<Status> statuses = new ArrayList<Status>();
        try {
            Paging page = new Paging(pageno, 2000);
            statuses.addAll(twitter.getUserTimeline(user, page));
            System.out.println("Total: " + statuses.size());
            CustomObject co;
            for (Status status : statuses) {
                String s = "@" + status.getUser().getScreenName() + ":" + status.getText();
                co = new CustomObject(status.getUser().getScreenName(), status.getText(), status.getText().length());
                System.out.println("*********sandeep***" + co.toString());
//


//        int d = s.length();
                System.out.println("Posting tweet number " + i++ + ". See Consumer for details.");
                ProducerRecord<String, CustomObject> rec = new ProducerRecord<>(topicName, co);

//        ProducerRecord<String, JSONObject> rec = new ProducerRecord<String, JSONObject>(topicName, obj);

                producer.send(rec);
                Thread.sleep(delay_ms);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        producer.close();
    }

    public static Twitter getTwitterinstance() throws IOException {

        FileInputStream twitterStream = null;
        Properties twitterProperties = new Properties();

        String twitterFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "twitter4j.properties";
        System.out.println("Reading config from " + twitterFile + "\n");
        twitterStream = new FileInputStream(twitterFile);

        twitterProperties.load(twitterStream);
        System.out.println("Displaying Twitter properties:\n");
        System.out.println("  oauth.consumerKey =       " + twitterProperties.getProperty("oauth.consumerKey"));
        System.out.println("  oauth.consumerSecret =    " + twitterProperties.getProperty("oauth.consumerSecret"));
        System.out.println("  oauth.accessToken =       " + twitterProperties.getProperty("oauth.accessToken"));
        System.out.println("  oauth.accessTokenSecret = " + twitterProperties.getProperty("oauth.accessTokenSecret"));

        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true).setOAuthConsumerKey(twitterProperties.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(twitterProperties.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(twitterProperties.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(twitterProperties.getProperty("oauth.accessTokenSecret"));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        System.out.println("\nReturning twitter instance ..............\n");
        return twitter;
    }


    public static List<Integer> method() {
        List<Integer> list = new ArrayList<>();

        try {
            Twitter twitter = new TwitterFactory().getInstance();
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            System.out.println("Showing available trends");
            for (Location location : locations) {
                System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
                list.add(location.getWoeid());
            }
            System.out.println("done.");
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
//            System.exit(-1);
        }
        return list;
    }

    public static List<TrendingNow> method2(int woe) {
        List<TrendingNow> trendingNowList = new ArrayList<>();
        try {
            int woeid = woe;
            Twitter twitter = new TwitterFactory().getInstance();
            Trends trends = twitter.getPlaceTrends(woeid);
            System.out.println("Showing trends for " + trends.getLocation().getName());

            TrendingNow trendingNow;
            for (Trend trend : trends.getTrends()) {
                System.out.println(String.format("%s (tweet_volume: %d)", trend.getName(), trend.getTweetVolume()));
                trendingNow = new TrendingNow(trend.getName(), trend.getTweetVolume());
                trendingNowList.add(trendingNow);
//                System.out.println(String.format("%s (tweet_URL: )", trend.getURL()));

            }

            System.out.println("done.");
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            System.exit(-1);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            System.out.println("WOEID must be number");
            System.exit(-1);
        }
        return trendingNowList;

    }

}
