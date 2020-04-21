package com.example.myapplication;

public class Bars {

    private String name;
    private double cost;
    private String address;

    public String getBarsName(){
        return name;
    }
    public void setBarsName(String name){
        this.name = name;
    }

    public void setBarsCost(double cost){
        this.cost = cost;
    }
    public double getBarsCost(){
        return cost;
    }

    public String getBarsAddress(){return address;}
    public  void setBarsAddress(String address){
        this.address = address;
    }
}
