package com.idanandben.finalapplicationproject.widgets;

public class BankTableBlock {
    private String name;

    private int color;
    private int col;
    private int row;
    private int locationX;
    private int locationY;
    private int initializedLocationX = -1;
    private int initializedLocationY = -1;

    public BankTableBlock(String name) {
        this.name = name;
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

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() { return color; }

    public int getInitializedLocationX() {
        return initializedLocationX;
    }

    public int getInitializedLocationY() {
        return initializedLocationY;
    }
}
