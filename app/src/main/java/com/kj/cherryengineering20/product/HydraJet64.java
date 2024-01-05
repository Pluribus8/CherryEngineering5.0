package com.kj.cherryengineering20.product;

import java.util.HashMap;

public class HydraJet64 extends HydraJet {

    private String name;
    protected double sixtyFourOzContainer;
    protected double sixtyFourOzWedge;
    protected double largeLid;
    private HashMap<String, Double> components;

    public HydraJet64() {
        name = "64oz HJ";
        hjTube = 0.000138889;
        sixtyFourOzContainer = 1;
        sixtyFourOzWedge = 1;
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

    public HashMap<String, Double> get64OzHjComponents() {
        components.put("HJ tube", hjTube);
        components.put("MWB tube", mwbTube);
        components.put("MWB slider", mwbSlider);
        components.put("HJ grommet", hjGrommet);
        components.put("HJ screw", hjScrew);
        components.put("64oz container", sixtyFourOzContainer);
        components.put("64oz wedge", sixtyFourOzWedge);
        components.put("HJ red cap", redCap);
        components.put("Large HJ lid", largeLid);

        return components;
    }
}
