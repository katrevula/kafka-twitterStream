package edu.nwmissouri.dv.twitterStreaming;

public class CustomObject {

    private String name;
//    private String text;
    private int count;
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
    public CustomObject(String name, int count) {
        this.name = name;
        this.count = count;
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
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
