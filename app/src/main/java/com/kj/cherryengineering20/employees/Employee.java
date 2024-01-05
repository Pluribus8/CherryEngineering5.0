package com.kj.cherryengineering20.employees;

public class Employee {

    private final String name;
    private double commissionRate;

    public Employee(String name, double commissionRate) {
        this.name = name;
        this.commissionRate = commissionRate;
    }

    public Employee(String name) {
        this.name = name;
        this.commissionRate = 0.85;
    }

    public String getName() {
        return name;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double rate) {
        this.commissionRate = rate;
    }

    public String toString() {
        return name + "," + commissionRate;
    }
}
