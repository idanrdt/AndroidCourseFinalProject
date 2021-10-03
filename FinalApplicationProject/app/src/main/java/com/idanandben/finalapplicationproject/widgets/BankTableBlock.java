package com.idanandben.finalapplicationproject.widgets;

import com.idanandben.finalapplicationproject.utilities.ConstProperties;

public class BankTableBlock {

    private final String name;
    private String atomicNumber;

    private int color;
    private int colorGroup;
    private int col;
    private int row;
    private int locationX;
    private int locationY;
    private int initializedLocationX = -1;
    private int initializedLocationY = -1;

    public BankTableBlock(String name) {
        this.name = name;
        this.atomicNumber = "";
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
        if(initializedLocationX == -1) {
            this.initializedLocationX = locationX;
        }
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
        if(initializedLocationY == -1) {
            this.initializedLocationY = locationY;
        }
    }

    public int getColor() { return color; }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getAtomicNumber() {
        return this.atomicNumber;
    }

    public void setAtomicNumber(String number) {
        this.atomicNumber = number;
    }

    public int getColorGroup() { return this.colorGroup; }

    public void setColorGroup(int group) { this.colorGroup = group; }

    public int getInitializedLocationX() {
        return initializedLocationX;
    }

    public int getInitializedLocationY() {
        return initializedLocationY;
    }
}
