package com.kj.cherryengineering20.product;

public class HydraJet extends Product {

    protected double hjTube;
    protected double mwbTube;
    protected double mwbSlider;
    protected double hjGrommet;
    protected double hjScrew;
    protected double container;
    protected double wedge;
    protected double redCap;
    protected int lid;
    protected double payrollPricePerCase;

    public HydraJet() {
        mwbTube = 0;
        mwbSlider = .000034722;
        hjGrommet = 1;
        hjScrew = 1;
        redCap = 1;
        payrollPricePerCase = 75;
    }

    public double getMwbTube() {
        return mwbTube;
    }

    public double getMwbSlider() {
        return mwbSlider;
    }

    public double getGrommet() {
        return hjGrommet;
    }

    public double getScrew() {
        return hjScrew;
    }

    public double getContainer() {
        return container;
    }

    public double getWedge() {
        return wedge;
    }

    public double getRedCap() {
        return redCap;
    }

    public int getLid() {
        return lid;
    }

    public double getPayrollPricePerCase() {
        return payrollPricePerCase;
    }

    public double getHjTube() {
        return hjTube;
    }
}
