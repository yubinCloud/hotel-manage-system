package com.yubin.hotelSys.model;

public class RoomType {
    private int id;
    private String name;
    private double stdPrice;
    private int peopleNum;
    private double area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(double stdPrice) {
        this.stdPrice = stdPrice;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
