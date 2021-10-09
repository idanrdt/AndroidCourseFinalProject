package com.idanandben.finalapplicationproject.widgets;

/**
 * Represent a single Bank block for game.
 */
public class BankTableBlock {

    private final String name;
    private final String symbol;
    private String atomicNumber;

    private int color;
    private int colorGroup;
    private int col;
    private int row;
    private int locationX;
    private int locationY;
    private int initializedLocationX = -1;
    private int initializedLocationY = -1;

    public BankTableBlock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
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

    /**
     * Gets the current block horizontal location on screen.
     * @return - The block horizontal location.
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * Set the current block horizontal location on the screen.
     * @param locationX - the block horizontal location.
     */
    public void setLocationX(int locationX) {
        this.locationX = locationX;
        if(initializedLocationX == -1) {
            this.initializedLocationX = locationX;
        }
    }

    /**
     * Gets the current block vertical location on screen.
     * @return - The block vertical location.
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * Set the current block vertical location on the screen.
     * @param locationY - the block vertical location.
     */
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

    public String getSymbol() { return symbol; }

    public String getAtomicNumber() {
        return this.atomicNumber;
    }

    public void setAtomicNumber(String number) {
        this.atomicNumber = number;
    }

    public int getColorGroup() { return this.colorGroup; }

    public void setColorGroup(int group) { this.colorGroup = group; }

    /**
     * Gets the initial block horizontal location.
     * @return - The block initial horizontal location.
     */
    public int getInitializedLocationX() {
        return initializedLocationX;
    }

    /**
     * Gets the initial block vertical location.
     * @return - The block initial vertical location.
     */
    public int getInitializedLocationY() {
        return initializedLocationY;
    }
}
