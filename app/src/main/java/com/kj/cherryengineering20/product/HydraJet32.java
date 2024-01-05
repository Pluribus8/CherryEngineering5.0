package com.kj.cherryengineering20.product;

import java.util.HashMap;

public class HydraJet32 extends HydraJet {

    protected double thirtyTwoOzContainer;
    protected double thirtyTwoOzWedge;
    protected double smallLid;
    private HashMap<String, Double> components;

    public HydraJet32() {
        hjTube = 0.000115741;
        thirtyTwoOzContainer = 1;
        thirtyTwoOzWedge = 1;
        smallLid = 1;
        components = new HashMap<>();
    }

    @Override
    public double getHjTube() {
        return hjTube;
    }

    public double getThirtyTwoOzContainer() {
        return thirtyTwoOzContainer;
    }

    public double getThirtyTwoOzWedge() {
        return thirtyTwoOzWedge;
    }

    public double getSmallLid() {
        return smallLid;
    }

    public HashMap<String, Double> get32OzHjComponents() {
        components.put("HJ tube", hjTube);
        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("HJ grommet", hjGrommet);
        components.put("HJ screw", hjScrew);
        components.put("32oz container", thirtyTwoOzContainer);
        components.put("32oz wedge", thirtyTwoOzWedge);
        components.put("HJ red cap", redCap);
        components.put("32oz lid", smallLid);

        return components;
    }
}
