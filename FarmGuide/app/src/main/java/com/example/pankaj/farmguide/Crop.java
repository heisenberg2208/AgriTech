package com.example.pankaj.farmguide;

public class Crop {

    private int id;
    private String data;
    private String cost;
    private int image;
    private int button;
    private double ph;

    public Crop(int id, String data, String cost, int image, int button, double ph)
    {
        this.id = id;
        this.data = data;
        this.cost = cost;
        this.image = image;
        this.button = button;
        this.ph = ph;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getCost() {
        return cost;
    }

    public int getImage() {
        return image;
    }

    public int getButton() {
        return button;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }
}
