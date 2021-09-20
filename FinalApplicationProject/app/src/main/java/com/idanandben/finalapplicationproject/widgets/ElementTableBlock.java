package com.idanandben.finalapplicationproject.widgets;

import com.idanandben.finalapplicationproject.utilities.Element;

public class ElementTableBlock {

    private Element element;
    private int col;
    private int row;
    private int color = 0xFFCCCCCC;

    public ElementTableBlock(Element element) {
        this.element = element;
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

    public Element getElement() {
        return element;
    }
}
