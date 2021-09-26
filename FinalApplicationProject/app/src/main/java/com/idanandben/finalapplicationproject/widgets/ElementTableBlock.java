package com.idanandben.finalapplicationproject.widgets;

import com.idanandben.finalapplicationproject.utilities.Element;

public class ElementTableBlock {

    private final Element element;
    private int col;
    private int row;
    private int locationX;
    private int locationY;
    private int initializedLocationX = -1;
    private int initializedLocationY = -1;
    private int color;
    private boolean isVisable;

    public ElementTableBlock(Element element) {
        this(element, 0xFFCCCCCC);
    }

    public ElementTableBlock(Element element, int color) {
        this.element = element;
        this.color = color;
        isVisable = true;
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

    public boolean getVisibility() {
        return this.isVisable;
    }

    public void setVisibility(boolean visable) {
        this.isVisable = visable;
    }

    public Element getElement() {
        return element;
    }

    public int getInitializedLocationX() {
        return initializedLocationX;
    }

    public int getInitializedLocationY() {
        return initializedLocationY;
    }
}