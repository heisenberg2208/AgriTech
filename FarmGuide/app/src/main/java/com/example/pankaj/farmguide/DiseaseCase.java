package com.example.pankaj.farmguide;

public class DiseaseCase {
    String user_id, lat, lon, prediction,url;

    public  DiseaseCase()
    {

    }

    public DiseaseCase(String user_id, String lat, String lon, String prediction,String url) {
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
        this.prediction = prediction;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}

