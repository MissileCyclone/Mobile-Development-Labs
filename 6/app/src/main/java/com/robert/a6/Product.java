package com.robert.a6;

public class Product {
    private final String title;
    private final String info;
    private final double cost;

    public Product(String title, String info, double cost) {
        this.title = title;
        this.info = info;
        this.cost = cost;
    }

    public String getName() {
        return title;
    }

    public String getDescription() {
        return info;
    }

    public double getPrice() {
        return cost;
    }
}