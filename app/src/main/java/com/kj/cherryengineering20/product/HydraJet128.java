package com.kj.cherryengineering20.product;

import java.util.HashMap;

public class HydraJet128 extends HydraJet {

    private String name;
    protected double OneTwentyEightContainer;
    protected double OneTwentyEightWedge;
    protected double largeLid;
    private HashMap<String, Double> components;

    public HydraJet128() {
        name = "128 HJ";
        hjTube = 0.00015625;
        OneTwentyEightContainer = 1;
        OneTwentyEightWedge = 1;
        largeLid = 1;
        components = new HashMap<>();
    }

    @Override
    public double getHjTube() {
        return hjTube;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public HashMap<String, Double> get128OzHjComponents() {
        components.put("HJ tube", hjTube);
        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("HJ grommet", hjGrommet);
        components.put("HJ screw", hjScrew);
        components.put("128oz container", OneTwentyEightContainer);
        components.put("128oz wedge", OneTwentyEightWedge);
        components.put("HJ red cap", redCap);
        components.put("Large HJ lid", largeLid);

        return components;
    }
}
