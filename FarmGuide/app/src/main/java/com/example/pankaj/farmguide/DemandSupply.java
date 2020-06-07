package com.example.pankaj.farmguide;

public class DemandSupply {
    String crop_name;
    String location;
    int year;
    double area;
    int supply;
    int demand;
    int price;

    public DemandSupply()
    {

    }

    public DemandSupply(String crop_name, String location, int year, double area, int supply, int demand,int price) {
        this.crop_name = crop_name;
        this.location = location;
        this.year = year;
        this.area = area;
        this.supply = supply;
        this.demand = demand;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }
}
