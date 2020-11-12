package edu.nwmissouri.dv.twitterStreaming;

public class CustomObject {

    private int top;
    private String name;
//    private String text;
    private int tweetCount;
//    private TrendingNow trendingNow;
//
//    public CustomObject(TrendingNow trendingNow) {
//        this.trendingNow = trendingNow;
//    }
//
//    public TrendingNow getTrendingNow() {
//        return trendingNow;
//    }
//
//    public void setTrendingNow(TrendingNow trendingNow) {
//        this.trendingNow = trendingNow;
//    }
//
//    public CustomObject() {
//    }
//
//    @Override
//    public String toString() {
//        return "CustomObject{" +
//                "trendingNow=" + trendingNow +
//                '}';
//    }

    //    public CustomObject(String name, String text, int count) {
//        this.name = name;
//        this.text = text;
//        this.count = count;
//    }
//
//    public CustomObject(String name, int tweetCount) {
//        this.name = name;
//        this.tweetCount = tweetCount;
//    }


    public CustomObject(int top, String name, int tweetCount) {
        this.top = top;
        this.name = name;
        this.tweetCount = tweetCount;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//


    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }
}
