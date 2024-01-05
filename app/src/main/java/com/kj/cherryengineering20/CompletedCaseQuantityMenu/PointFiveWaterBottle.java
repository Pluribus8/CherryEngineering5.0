package com.kj.cherryengineering20.CompletedCaseQuantityMenu;

import com.kj.cherryengineering20.product.Product;
import com.kj.cherryengineering20.product.WaterBottle;

import java.util.HashMap;

public class PointFiveWaterBottle extends WaterBottle {

    private final double mwbTube;
    private final double mwbSlider;
    private final double screws;
    private final double caps;
    private final double bottles;
    private final double cross;
    private final double oRing;
    private final double endCap;
    private final double payrollPricePerCase;

    public PointFiveWaterBottle() {
        mwbTube = 0.000384838;
        mwbSlider = 0.000017361;
        screws = 1;
        caps = 1;
        bottles = 1;
        cross = 1;
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
        return bottles;
    }

    public double getCross() {
        return cross;
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

    public HashMap<String, Double> getPointFiveComponents() {
        HashMap<String, Double> components = new HashMap<>();

        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("MWB screws", screws);
        components.put("Bottle caps", caps);
        components.put("0.5 bottles", bottles);
        components.put("0.5 crosses", cross);
        components.put("MWB o-ring", oRing);
        components.put("MWB end cap", endCap);

        return components;
    }
}
