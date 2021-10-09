package com.idanandben.finalapplicationproject.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Periodic Table creation.
 */
public class PeriodicTableView extends View {

    private final ArrayList<TableElementBlock> tableBlocks = new ArrayList<>();
    private final ArrayList<BankTableBlock> bankTargets = new ArrayList<>();

    private final Paint atomicNumberPaint;
    private final Paint blockPaint = new Paint();
    private final Paint blockStroke = new Paint();
    private final Paint symbolPaint;
    private final Paint weightPaint;
    private final Paint legendPaint;

    private final Rect drawingCursor = new Rect();
    private final Rect tableRect = new Rect();

    private final int rowAmount = 7;
    private final int colAmount = 18;
    private int tableBlockSize;
    private int bankBlockSize;
    private int level;

    private BankTableBlock selectedBlock;

    private boolean isTableWorking;

    /**
     * Event interface for the table.
     */
    public interface TableStateListeners {
        void onCorrectElementPlaced();
        void onWrongElementPlaced();
        void onTableCompleted();
    }

    private List<TableStateListeners> stateListenersList;

    public PeriodicTableView(Context context) {
        this(context, null, 0);
    }

    public PeriodicTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PeriodicTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        atomicNumberPaint = new Paint();
        atomicNumberPaint.setAntiAlias(true);
        atomicNumberPaint.setColor(Color.BLACK);

        symbolPaint = new Paint(atomicNumberPaint);
        symbolPaint.setTextAlign(Paint.Align.CENTER);

        weightPaint = new Paint(symbolPaint);
        weightPaint.setSubpixelText(true);
        legendPaint = new Paint(symbolPaint);

        blockPaint.setColor(Color.WHITE);
        blockStroke.setStyle(Paint.Style.STROKE);
        blockStroke.setColor(Color.BLACK);
        blockStroke.setStrokeWidth(2);
    }

    /**
     * Initialize the table properties.
     * @param elementsList - The Periodic Table elements.
     * @param screenWidth - The current screen width.
     * @param screenHeight - The current screen height.
     * @param bankBlocks - The bank blocks.
     * @param level - The current level.
     */
    public void initializeTable(List<TableElementBlock> elementsList, int screenWidth, int screenHeight, List<BankTableBlock> bankBlocks, int level) {
        tableBlocks.clear();
        bankTargets.clear();
        tableBlocks.addAll(elementsList);
        bankTargets.addAll((bankBlocks));
        stateListenersList = new ArrayList<>();

        tableRect.left = 0;
        tableRect.right = screenWidth;
        tableRect.top = 0;
        tableRect.bottom = screenHeight;
        this.level = level;

        measureCanvas();
        int bankDrawingOffset;
        int bankAmount;

        if(level == 3 && bankBlocks.size() > 8) {
            bankAmount = 8;
        } else {
            bankAmount = bankBlocks.size();
        }

        if(bankAmount % 2 == 0) {
            bankDrawingOffset = (screenWidth / 2) - (bankAmount / 2) * (bankBlockSize + bankAmount);
        } else {
            bankAmount--;
            bankDrawingOffset = (screenWidth / 2) - (bankAmount / 2) * (bankBlockSize + bankAmount);
            bankDrawingOffset -= (bankBlockSize / 2);
        }

        int tableDrawingOffset = (screenWidth / 2) - (colAmount * (tableBlockSize + colAmount / 2)) / 2;

        //initialize table
        for(TableElementBlock block : tableBlocks) {
            block.setLocationY(tableBlockSize * block.getCol() + tableDrawingOffset);
            block.setLocationX(tableBlockSize * block.getRow());
        }

        //initialize bank
        for(BankTableBlock bankBlock : bankTargets) {
            bankBlock.setLocationY((bankBlockSize / 2) + (bankBlock.getCol() - 1) * bankBlockSize + bankDrawingOffset);
            bankBlock.setLocationX(tableBlockSize * bankBlock.getRow());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * Add listener to table events.
     * @param listeners - The listener to add.
     */
    public void addTableListener(TableStateListeners listeners) {
        this.stateListenersList.add(listeners);
    }

    /**
     * Set if the table is interacting with the user.
     * @param enabled
     */
    public void setTableEnabled(boolean enabled) {
        isTableWorking = enabled;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (level) {
            case 1: {
                drawLevel1Table(canvas);
                break;
            }
            case 2: {
                drawLevel2Table(canvas);
                break;
            }
            case 3: {
                drawLevel3Table(canvas);
            }
        }
    }

    /**
     * Drawing the level 1 table.
     * @param canvas
     */
    private void drawLevel1Table(Canvas canvas) {
        int boxSize = tableBlockSize / 2 - 1;
        double symbolOffset = 2.8;

        for(TableElementBlock block : tableBlocks) {
            drawingCursor.right = block.getLocationY() + boxSize;
            drawingCursor.left = block.getLocationY() - boxSize;
            drawingCursor.bottom = block.getLocationX() + boxSize;
            drawingCursor.top = block.getLocationX() - boxSize;
            canvas.drawRect(drawingCursor, blockStroke);

            if(block.getVisibility()) {
                blockPaint.setColor(block.getColor());

                canvas.drawRect(drawingCursor, blockPaint);

                canvas.drawText(block.getElementSymbol(), drawingCursor.left + tableBlockSize / 2f,
                        drawingCursor.bottom - (int) (tableBlockSize / 2.8), symbolPaint);

                if(!(block.getElementSymbol().contains("*"))) {
                    canvas.drawText(String.valueOf(block.getBlockAtomicNumber()), drawingCursor.left + tableBlockSize / 20f,
                            drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

                    canvas.drawText(String.valueOf(block.getBlockWeight()), drawingCursor.left + tableBlockSize / 2f,
                            drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
                }
            }
        }

        boxSize = bankBlockSize / 2 - 1;

        if(bankTargets.size() == 0) {
            finalizeTable();
        } else if(!bankTargets.get(0).getAtomicNumber().equals("")) {
            symbolOffset = 5;
        }

        for(BankTableBlock bank : bankTargets) {

            drawingCursor.right = bank.getLocationY() + boxSize;
            drawingCursor.left = bank.getLocationY() - boxSize;
            drawingCursor.bottom = bank.getLocationX() + boxSize;
            drawingCursor.top = bank.getLocationX() - boxSize;

            blockPaint.setColor(bank.getColor());

            canvas.drawRect(drawingCursor, blockStroke);
            canvas.drawRect(drawingCursor, blockPaint);
            canvas.drawText(bank.getSymbol(), drawingCursor.left + bankBlockSize /2f, drawingCursor.bottom - (int)(bankBlockSize / symbolOffset), symbolPaint);
            canvas.drawText(bank.getAtomicNumber(), drawingCursor.left + tableBlockSize / 20f,drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);
        }
    }

    /**
     * Drawing the level 2 table.
     * @param canvas
     */
    private void drawLevel2Table(Canvas canvas) {
        int boxSize = tableBlockSize / 2 - 1;
        TableElementBlock selectedBlock = null;

        for(TableElementBlock block : tableBlocks) {
            blockPaint.setColor(block.getColor());

            if(block.getVisibility()) {
                selectedBlock = block;
            }

            drawingCursor.right = block.getLocationY() + boxSize;
            drawingCursor.left = block.getLocationY() - boxSize;
            drawingCursor.bottom = block.getLocationX() + boxSize;
            drawingCursor.top = block.getLocationX() - boxSize;

            canvas.drawRect(drawingCursor, blockStroke);

            canvas.drawRect(drawingCursor, blockPaint);

            canvas.drawText(block.getElementSymbol(), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - (int) (tableBlockSize / 2.8), symbolPaint);

            if(!(block.getElementSymbol().contains("*"))) {
                canvas.drawText(String.valueOf(block.getBlockAtomicNumber()), drawingCursor.left + tableBlockSize / 20f,
                        drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

                canvas.drawText(String.valueOf(block.getBlockWeight()), drawingCursor.left + tableBlockSize / 2f,
                        drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
            }
        }

        float previousStrokeWidth = blockStroke.getStrokeWidth();

        if(selectedBlock != null) {
            boxSize += 15;
            drawingCursor.right = selectedBlock.getLocationY() + boxSize;
            drawingCursor.left = selectedBlock.getLocationY() - boxSize;
            drawingCursor.bottom = selectedBlock.getLocationX() + boxSize;
            drawingCursor.top = selectedBlock.getLocationX() - boxSize;

            blockPaint.setColor(selectedBlock.getColor());
            blockStroke.setStrokeWidth(previousStrokeWidth * 15);

            canvas.drawRect(drawingCursor, blockStroke);

            canvas.drawRect(drawingCursor, blockPaint);

            canvas.drawText(selectedBlock.getElementSymbol(), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - (int) (tableBlockSize / 2.8 + 10), symbolPaint);

            canvas.drawText(String.valueOf(selectedBlock.getBlockAtomicNumber()), drawingCursor.left + tableBlockSize / 20f,
                    drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

            canvas.drawText(String.valueOf(selectedBlock.getBlockWeight()), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
        }

        blockStroke.setStrokeWidth(previousStrokeWidth);


        if(bankTargets.size() == 0) {
            finalizeTable();
        }

        boxSize = bankBlockSize / 2 - 1;

        for(BankTableBlock bank : bankTargets) {
            drawingCursor.right = bank.getLocationY() + boxSize;
            drawingCursor.left = bank.getLocationY() - boxSize;
            drawingCursor.bottom = bank.getLocationX() + boxSize / 2;
            drawingCursor.top = bank.getLocationX() - boxSize / 2;

            blockPaint.setColor(bank.getColor());

            canvas.drawRect(drawingCursor, blockStroke);
            canvas.drawRect(drawingCursor, blockPaint);
            drawingCursor.bottom -=  boxSize / 4;
            drawingCursor.left += boxSize;
            canvas.drawText(bank.getName().substring(bank.getName().indexOf(" ")), drawingCursor.left, drawingCursor.bottom, legendPaint);
            drawingCursor.bottom -= (int)weightPaint.getTextSize() * 1.5;
            canvas.drawText(bank.getName().substring(0, bank.getName().indexOf(" ")), drawingCursor.left, drawingCursor.bottom, legendPaint);
        }
    }

    /**
     * Drawing the level 3 table.
     * @param canvas
     */
    private void drawLevel3Table(Canvas canvas) {
        int boxSize = tableBlockSize / 2 - 1;

        for(TableElementBlock block : tableBlocks) {
            drawingCursor.right = block.getLocationY() + boxSize;
            drawingCursor.left = block.getLocationY() - boxSize;
            drawingCursor.bottom = block.getLocationX() + boxSize;
            drawingCursor.top = block.getLocationX() - boxSize;
            canvas.drawRect(drawingCursor, blockStroke);

            blockPaint.setColor(block.getColor());

            canvas.drawRect(drawingCursor, blockPaint);

            canvas.drawText(block.getElementSymbol(), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - (int) (tableBlockSize / 2.8), symbolPaint);

            if(!(block.getElementSymbol().contains("*"))) {
                canvas.drawText(String.valueOf(block.getBlockAtomicNumber()), drawingCursor.left + tableBlockSize / 20f,
                        drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

                canvas.drawText(String.valueOf(block.getBlockWeight()), drawingCursor.left + tableBlockSize / 2f,
                        drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
            }
        }

        boxSize = bankBlockSize / 2 - 1;

        if(bankTargets.size() == 0) {
            stateListenersList.forEach(TableStateListeners::onTableCompleted);
        }
        
        for(BankTableBlock bank : bankTargets) {
            drawingCursor.right = bank.getLocationY() + boxSize;
            drawingCursor.left = bank.getLocationY() - boxSize;
            drawingCursor.bottom = bank.getLocationX() + boxSize / 3;
            drawingCursor.top = bank.getLocationX() - boxSize / 3;

            blockPaint.setColor(bank.getColor());

            canvas.drawRect(drawingCursor, blockStroke);
            canvas.drawRect(drawingCursor, blockPaint);
            drawingCursor.bottom -=  boxSize / 4;
            drawingCursor.left += boxSize;
            canvas.drawText(bank.getName(), drawingCursor.left, drawingCursor.bottom, legendPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTableWorking) {
            if(level != 2) {
                dragBankElement(event);
            } else {
                clickTableElement(event);
            }
        }

        return true;
    }

    /**
     * Set the related element to be bigger.
     * @param symbol - The element symbol.
     * @param increased - True - Bigger, False - normal state.
     */
    public void setIncreasedElement(String symbol, boolean increased) {
        for(TableElementBlock block : tableBlocks) {
            if(block.getElementSymbol().equals(symbol)) {
                block.setVisibility(increased);
                break;
            }
        }

        invalidate();
    }

    /**
     * Handles table click event.
     * @param event - The MotionEvent recived.
     */
    private void clickTableElement(MotionEvent event) {
        int size = tableBlockSize / 2 + 1;
        TableElementBlock tableBlock = null;
        for(TableElementBlock block : tableBlocks) {
            if(block.getVisibility()) {
                tableBlock = block;
                break;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for (BankTableBlock block : bankTargets) {
                if (((int) event.getX() > block.getLocationY() - size && (int) event.getX() < block.getLocationY() + size) &&
                        (int) event.getY() >= block.getLocationX() - size && (int) event.getY() <= block.getLocationX() + size) {
                    if(tableBlock != null) {
                        if(tableBlock.getColorGroup() == block.getColorGroup()) {
                            tableBlock.setColor(block.getColor());
                            stateListenersList.forEach(TableStateListeners::onCorrectElementPlaced);
                        } else {
                            stateListenersList.forEach(TableStateListeners::onWrongElementPlaced);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Handles the Drag element event.
     * @param event
     */
    private void dragBankElement(MotionEvent event) {
        int size = tableBlockSize / 2 + 1;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                selectedBlock = null;
                for (BankTableBlock block : bankTargets) {
                    if (((int) event.getX() > block.getLocationY() - size && (int) event.getX() < block.getLocationY() + size) &&
                            (int) event.getY() >= block.getLocationX() - size && (int) event.getY() <= block.getLocationX() + size) {
                        selectedBlock = block;
                        break;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (selectedBlock != null) {
                    selectedBlock.setLocationY((int) event.getX());
                    selectedBlock.setLocationX((int) event.getY());
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (selectedBlock != null) {

                    for (TableElementBlock elementBlock : tableBlocks) {
                        if ((selectedBlock.getLocationX() > elementBlock.getLocationX() - size && selectedBlock.getLocationX() < elementBlock.getLocationX() + size) &&
                                (selectedBlock.getLocationY() > elementBlock.getLocationY() - size && selectedBlock.getLocationY() < elementBlock.getLocationY() + size)) {
                            if(elementBlock.getElementSymbol().equals(selectedBlock.getSymbol())) {
                                elementBlock.setVisibility(true);
                                bankTargets.remove(selectedBlock);
                                selectedBlock = null;
                                invalidate();
                                stateListenersList.forEach(TableStateListeners::onCorrectElementPlaced);
                                break;
                            } else {
                                stateListenersList.forEach(TableStateListeners::onWrongElementPlaced);
                            }
                        }
                    }

                    if (selectedBlock != null) {
                        selectedBlock.setLocationX(selectedBlock.getInitializedLocationX());
                        selectedBlock.setLocationY(selectedBlock.getInitializedLocationY());
                        selectedBlock = null;
                        invalidate();
                    }
                    break;
                }
            }
        }
    }

    /**
     * Measure the canvas for screen fitting.
     */
    private void measureCanvas() {
        final int blockWidth = (tableRect.width() / (colAmount + 3));
        final int blockHeight = tableRect.height() / (rowAmount + 3);
        tableBlockSize = Math.min(blockWidth, blockHeight);

        symbolPaint.setTextSize(tableBlockSize / 2f);
        atomicNumberPaint.setTextSize(tableBlockSize / 4f);
        weightPaint.setTextSize(tableBlockSize / 5f);
        legendPaint.setTextSize(tableBlockSize / 3f);
        setBankBlockSizeByLevel();
    }

    /**
     * Set the Bank size
     */
    private void setBankBlockSizeByLevel() {
        switch(level) {
            case 2: {
                bankBlockSize = (int)(tableBlockSize * 2.5);
                break;
            }
            case 3: {
                bankBlockSize = (int)(tableBlockSize * 2.3);
                break;
            }
            default: {
                bankBlockSize = tableBlockSize;
                break;
            }
        }
    }

    /**
     * Finish the level, remove all elements.
     */
    private void finalizeTable() {
        tableBlocks.clear();
        bankTargets.clear();
        stateListenersList.forEach(TableStateListeners::onTableCompleted);
    }
}
