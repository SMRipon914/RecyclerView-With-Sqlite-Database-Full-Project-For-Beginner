package com.creativeapp.rllll;

public class Data {
    private int id;
    private String data;
    private String timeStamp;

    public Data() {

    }

    public Data(int id, String data, String timeStamp) {
        this.id = id;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}