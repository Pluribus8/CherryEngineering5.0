package com.kj.cherryengineering20.product;

public class Product {

    private String name;
    private double pricePerCase;
    private double mwbTube;
    private double mwbSlider;
    private double hjTube;

    public Product() {

    }

    public Product(String name) {

    }

    public Product(String name, double pricePerCase) {
        this.name = name;
        this.pricePerCase = pricePerCase;
        this.mwbTube = 0;
        this.mwbSlider = 0;
        this.hjTube = 0;
    }

    public double getMwbTube() {
        return mwbTube;
    }

    public double getMwbSlider() {
        return mwbSlider;
    }

    public double getHjTube() {
        return hjTube;
    }

    public String getName() {
        return this.name;
    }

    public double getPricePerCase() {
        return this.pricePerCase;
    }

    public String toString() {
        return this.name;
    }

    public double getPayrollPricePerCase() {
        return pricePerCase;
    }
//    public double getMwbTube() {
//        return mwbTube;
//    }
//
//    public double getMwbSlider() {
//        return mwbSlider;
//    }
//
//    public double getHjTube() {
//        return hjTube;
//    }
}