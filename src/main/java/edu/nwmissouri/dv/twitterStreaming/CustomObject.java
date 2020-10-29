package edu.nwmissouri.dv.twitterStreaming;

public class CustomObject {

    private String name;
    private String text;
    private int count;

    public CustomObject() {
    }

    public CustomObject(String name, String text, int count) {
        this.name = name;
        this.text = text;
        this.count = count;
    }

    public CustomObject(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
