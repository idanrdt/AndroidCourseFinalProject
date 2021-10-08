package com.idanandben.finalapplicationproject.utilities;

public class Element {

    public final int atomicNumber;
    public final String symbol;
    public final String name;
    public final int period;
    public final int group;
    public final double weight;
    public final int colorGroup;
    public final String familyName;

    public Element(int atomicNumber, String symbol, String name, int period, int group, double weight, int colorGroup, String familyName) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.period = period;
        this.group = group;
        this.weight = weight;
        this.colorGroup = colorGroup;
        this.familyName = familyName;
    }
}
