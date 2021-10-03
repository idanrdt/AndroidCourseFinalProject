package com.idanandben.finalapplicationproject.widgets;

import com.idanandben.finalapplicationproject.utilities.ConstProperties;
import com.idanandben.finalapplicationproject.utilities.Element;

public class TableElementBlock {

    private final Element element;
    private final String atomicNumber;
    private final String weight;
    private final int col;
    private final int row;
    private int locationX;
    private int locationY;
    private int color;
    private boolean isVisible;

    public TableElementBlock(Element element) {
        this(element, ConstProperties.GENERIC_COLOR);
    }

    public TableElementBlock(Element element, int color) {
        this.element = element;
        this.color = color;
        this.atomicNumber = String.valueOf(element.atomicNumber);
        this.weight = String.valueOf(element.weight);
        this.row = element.period;
        this.col = element.group;
        isVisible = true;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public boolean getVisibility() {
        return this.isVisible;
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
    }

    public int getColorGroup() { return this.element.colorGroup; }

    public String getBlockAtomicNumber() {
        return this.atomicNumber;
    }

    public String getBlockWeight() {
        return this.weight;
    }

    public String getElementSymbol() {
        return this.element.symbol;
    }
}