package com.kj.cherryengineering20.product;

import java.util.HashMap;

public class OnePointFiveMwb extends WaterBottle {

    private final double mwbTube;
    private final double mwbSlider;
    private final double screws;
    private final double caps;
    private final double onePointFiveBottles;
    private final double onePointFiveCross;
    private final double oRing;
    private final double endCap;
    private final double payrollPricePerCase;

    public OnePointFiveMwb() {
        mwbTube = 0.0005613426;
        mwbSlider = 0.000017361;
        screws = 1;
        caps = 1;
        onePointFiveBottles = 1;
        onePointFiveCross = 1;
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
        return caps;
    }

    public double getBottles() {
        return onePointFiveBottles;
    }

    public double getCross() {
        return onePointFiveCross;
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

    public HashMap<String, Double> getOnePointFiveComponents() {
        HashMap<String, Double> components = new HashMap<>();

        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("MWB screws", screws);
        components.put("Bottle caps", caps);
        components.put("1.5 bottles", onePointFiveBottles);
        components.put("1.5 crosses", onePointFiveCross);
        components.put("MWB o-ring", oRing);
        components.put("MWB end cap", endCap);

        return components;
    }
}
