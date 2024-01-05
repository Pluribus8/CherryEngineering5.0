package com.kj.cherryengineering20.product;

import java.util.HashMap;

public class TwoPointTwoMwb extends WaterBottle {

    private final double mwbTube;
    private final double mwbSlider;
    private final double screws;
    private final double largeLid;
    private final double twoPointTwoBottles;
    private final double twoPointTwoCross;
    private final double oRing;
    private final double endCap;
    private final double payrollPricePerCase;

    public TwoPointTwoMwb() {
        mwbTube = 0.0005613426;
        mwbSlider = 0.000017361;
        screws = 1;
        largeLid = 1;
        twoPointTwoBottles = 1;
        twoPointTwoCross = 1;
        oRing = 1;
        endCap = 1;
        payrollPricePerCase = 41;
    }

    public double getMwbTube() {
        return mwbTube;
    }

    public double getMwbSlider() {
        return mwbSlider;
    }

    public double getScrews() {
        return screws;
    }

    public double getCaps() {
        return largeLid;
    }

    public double getBottles() {
        return twoPointTwoBottles;
    }

    public double getCross() {
        return twoPointTwoCross;
    }

    public double getoRing() {
        return oRing;
    }

    public double getEndCap() {
        return endCap;
    }
    public double getPayrollPricePerCase() {
        return payrollPricePerCase;
    }

    public HashMap<String, Double> getTwoPointTwoComponents() {
        HashMap<String, Double> components = new HashMap<>();

        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("MWB screws", screws);
        components.put("Large HJ lid", largeLid);
        components.put("2.2 bottles", twoPointTwoBottles);
        components.put("2.2 crosses", twoPointTwoCross);
        components.put("MWB o-ring", oRing);
        components.put("MWB end cap", endCap);

        return components;
    }
}
