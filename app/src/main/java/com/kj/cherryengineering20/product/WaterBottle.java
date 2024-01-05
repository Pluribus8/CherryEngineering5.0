package com.kj.cherryengineering20.product;

public class WaterBottle extends Product {

    private double mwbTube;
    private double mwbSlider;
    private double screws;
    private double caps;
    private double bottles;
    private double cross;
    private double oRing;
    private double endCap;
    private double payrollPricePerCase;

    public WaterBottle() {
        mwbSlider = 0.000017361;
        screws = 1;
        oRing = 1;
        endCap = 1;
        payrollPricePerCase = 41;
    }

    public double getPayrollPricePerCase() {
        return payrollPricePerCase;
    }
}
