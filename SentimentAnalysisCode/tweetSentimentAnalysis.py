from tweepy import API
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import string
from textblob import TextBlob
import twitter_cred
import numpy as np
import pandas as pd
import re
from tweepy import Cursor
from sklearn.metrics import accuracy_score
import matplotlib.pyplot as plt
pd.set_option('display.width', 1000)
pd.set_option("display.max_columns", 100)


# # # # TWITTER CLIENT # # # #
class TwitterClient():
    def __init__(self, twitter_user=None):
        self.auth = TwitterAuthenticator().authenticate_twitter_app()
        self.twitter_client = API(self.auth)
        self.twitter_user = twitter_user

    def get_twitter_client_api(self):
        return self.twitter_client


# # # # TWITTER AUTHENTICATER # # # #
class TwitterAuthenticator():

    def authenticate_twitter_app(self):
        auth = OAuthHandler(twitter_cred.CONSUMER_KEY, twitter_cred.CONSUMER_SECRET)
        auth.set_access_token(twitter_cred.ACCESS_TOKEN, twitter_cred.ACCESS_TOKEN_SECRET)
        return auth


# # # # TWITTER STREAMER # # # #
class TwitterStreamer():
    """
    Class for streaming and processing live tweets.
    """

    def __init__(self):
        self.twitter_autenticator = TwitterAuthenticator()

    def stream_tweets(self, fetched_tweets_filename, hash_tag_list):
        # This handles Twitter authentication and the connection to Twitter Streaming API
        listener = TwitterListener(fetched_tweets_filename)
        auth = self.twitter_autenticator.authenticate_twitter_app()
        stream = Stream(auth, listener)

        # This line filter Twitter Streams to capture data by the keywords:
        stream.filter(track=hash_tag_list)


# # # # TWITTER STREAM LISTENER # # # #
class TwitterListener(StreamListener):
    """
    This listener class prints received tweets to stdout.
    """

    def __init__(self, fetched_tweets_filename):
        self.fetched_tweets_filename = fetched_tweets_filename

    def on_data(self, data):
        try:
            print(data)
            with open(self.fetched_tweets_filename, 'a') as tf:
                tf.write(data)
            return True
        except BaseException as e:
            print("Error on_data %s" % str(e))
        return True

    def on_error(self, status):
        if status == 420:
            # Returning False on_data method in case rate limit occurs.
            return False
        print(status)


class TweetAnalyzer():
    """
    Functionality for analyzing and categorizing content from tweets.
    """

    def clean_tweet(self, tweet):
        return ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t])|(\w+:\/\/\S+)", " ", tweet).split())

    def analyze_sentiment(self, tweet):
        analysis = TextBlob(self.clean_tweet(tweet))
        if analysis.sentiment.polarity > 0:
            return 1
        elif analysis.sentiment.polarity == 0:
            return 0
        else:
            return -1

    def my_sentiment_analyser(self, tweet):
        stop_words = ["i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself",
                      "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself",
                      "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
                      "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
                      "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as",
                      "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through",
                      "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off",
                      "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how",
                      "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not",
                      "only", "own", "same", "so", "than", "too", "very", "can", "will", "just", "don", "should", "now"]

        text = tweet.lower()
        tokenized_words = tweet_analyzer.clean_tweet(text).split()

        myPositiveWordCount = 0
        myNegativeWordCount = 0

        # positiveTweetCount = 0
        # negativeTweetCount = 0
        # neutralTweetCount = 0

        for word in tokenized_words:
            if word not in stop_words:
                with open('pos.txt', 'r') as file:
                    for line in file:
                        if word in line:
                            if len(word) == len(line)-1:
                                myPositiveWordCount = myPositiveWordCount + 1

                with open('neg.txt', 'r') as file:
                    for line in file:
                        if word in line:
                            if len(word) == len(line)-1:
                                myNegativeWordCount = myNegativeWordCount + 1
        # print(myPositiveWordCount)
        # print(myNegativeWordCount)
        # print("---------------------")
        if myNegativeWordCount == myPositiveWordCount:
            return 0
        elif myNegativeWordCount > myPositiveWordCount:
            return -1
        elif myNegativeWordCount < myPositiveWordCount:
            return 1



    def tweets_to_data_frame(self, tweets):
        df = pd.DataFrame(data=[tweet.text for tweet in tweets], columns=['tweets'])

        df['id'] = np.array([tweet.id for tweet in tweets])
        df['len'] = np.array([len(tweet.text) for tweet in tweets])
        df['date'] = np.array([tweet.created_at for tweet in tweets])
        df['source'] = np.array([tweet.source for tweet in tweets])
        df['likes'] = np.array([tweet.favorite_count for tweet in tweets])
        df['retweets'] = np.array([tweet.retweet_count for tweet in tweets])
        return df


if __name__ == '__main__':
    twitter_client = TwitterClient()
    tweet_analyzer = TweetAnalyzer()

    api = twitter_client.get_twitter_client_api()
    tweets = api.user_timeline(screen_name="KamalaHarris", count=50)
    df = tweet_analyzer.tweets_to_data_frame(tweets)

    # sentiment calculation using textblob
    df['sentiment'] = np.array([tweet_analyzer.analyze_sentiment(tweet) for tweet in df['tweets']])
    df['mySentiment'] = np.array([tweet_analyzer.my_sentiment_analyser(tweet) for tweet in df['tweets']])

    # for accuracy calculation
    accuracy = int(accuracy_score(df['sentiment'], df['mySentiment']) * 100)
    acc = str(accuracy)

    # for visualization and comparing both outputs
    positiveCount = 0
    negativeCount = 0
    neutralCount = 0
    for value in df['sentiment']:
        if value == 0:
            neutralCount = neutralCount + 1
        elif value == 1:
            positiveCount = positiveCount + 1
        else:
            negativeCount = negativeCount + 1
    # print("Positive Count: ", positiveCount)
    # print("Negative Count: ", negativeCount)
    # print("Neutral Count: ", neutralCount)

    myPositiveCount = 0
    myNegativeCount = 0
    myNeutralCount = 0
    for value in df['mySentiment']:
        if value == 0:
            myNeutralCount = myNeutralCount + 1
        elif value == 1:
            myPositiveCount = myPositiveCount + 1
        else:
            myNegativeCount = myNegativeCount + 1

    myValues = [myPositiveCount, myNeutralCount, myNegativeCount]
    orgValues = [positiveCount, neutralCount, negativeCount]

    index = np.arange(3)
    bar_width = 0.25

    fig, ax = plt.subplots()
    myValuesBar = ax.bar(index, myValues, bar_width, label="manual calculation")
    orgValuesBar = ax.bar(index + bar_width, orgValues, bar_width, label="using Textblob")
    ax.set_xlabel('Sentiment values')
    ax.set_ylabel('Count')
    ax.set_title('Sentiment analysis on latest 50 tweets. Accuracy: ' + acc + '%')
    ax.set_xticks(index + bar_width / 2)
    ax.set_xticklabels(["positive", "neutral", "negative"])
    ax.get_children()[0].set_color('g')
    ax.get_children()[1].set_color('b')
    ax.get_children()[2].set_color('r')
    ax.legend()
    plt.show()

    # print(df.head(50)) # to print whole dataframe

