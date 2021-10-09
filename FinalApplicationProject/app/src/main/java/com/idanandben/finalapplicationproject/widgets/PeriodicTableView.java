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

public class PeriodicTableView extends View {

    private final ArrayList<TableElementBlock> tableBlocks = new ArrayList<>();
    private final ArrayList<BankTableBlock> bankTargets = new ArrayList<>();

    private final Paint atomicNumberPaint;
    private final Paint blockPaint = new Paint();
    private final Paint blockStroke = new Paint();
    private final Paint symbolPaint;
    private final Paint weightPaint;
    private final Paint legendPaint;
    private final Paint bgPaint = new Paint();

    private final Rect drawingCursor = new Rect();
    private final Rect tableRect = new Rect();

    private final int rowAmount = 7;
    private final int colAmount = 18;
    private int tableBlockSize;
    private int bankBlockSize;
    private int level;

    private BankTableBlock selectedBlock;

    private boolean isTableWorking;

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

        bgPaint.setColor(Color.WHITE);
        blockPaint.setColor(Color.WHITE);
        blockStroke.setStyle(Paint.Style.STROKE);
        blockStroke.setColor(Color.BLACK);
        blockStroke.setStrokeWidth(2);
    }

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
        int startBankOffset;
        int bankAmount;

        if(level == 3 && bankBlocks.size() > 8) {
            bankAmount = 8;
        } else {
            bankAmount = bankBlocks.size();
        }

        if(bankAmount % 2 == 0) {
            startBankOffset = (screenWidth / 2) - (bankAmount / 2) * (bankBlockSize + bankAmount);
        } else {
            bankAmount--;
            startBankOffset = (screenWidth / 2) - (bankAmount / 2) * (bankBlockSize + bankAmount);
            startBankOffset += (bankBlockSize / 2);
        }

        int startTableOffset = (screenWidth / 2) - (colAmount * (tableBlockSize + colAmount / 2)) / 2;

        for(TableElementBlock block : tableBlocks) {
            block.setLocationY(tableBlockSize * block.getCol() + startTableOffset);
            block.setLocationX(tableBlockSize * block.getRow());
        }

        for(BankTableBlock bankBlock : bankTargets) {
            bankBlock.setLocationY((bankBlockSize / 2) + (bankBlock.getCol() - 1) * bankBlockSize + startBankOffset);
            bankBlock.setLocationX(tableBlockSize * bankBlock.getRow());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void addTableListener(TableStateListeners listeners) {
        this.stateListenersList.add(listeners);
    }

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

        if(selectedBlock != null) {
            boxSize += 15;
            drawingCursor.right = selectedBlock.getLocationY() + boxSize;
            drawingCursor.left = selectedBlock.getLocationY() - boxSize;
            drawingCursor.bottom = selectedBlock.getLocationX() + boxSize;
            drawingCursor.top = selectedBlock.getLocationX() - boxSize;

            blockPaint.setColor(selectedBlock.getColor());
            blockStroke.setStrokeWidth(30);

            canvas.drawRect(drawingCursor, blockStroke);

            canvas.drawRect(drawingCursor, blockPaint);

            canvas.drawText(selectedBlock.getElementSymbol(), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - (int) (tableBlockSize / 2.8 + 10), symbolPaint);

            canvas.drawText(String.valueOf(selectedBlock.getBlockAtomicNumber()), drawingCursor.left + tableBlockSize / 20f,
                    drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

            canvas.drawText(String.valueOf(selectedBlock.getBlockWeight()), drawingCursor.left + tableBlockSize / 2f,
                    drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
        }
        blockStroke.setStrokeWidth(2);


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

    public void setIncreasedElement(String symbol, boolean increased) {
        for(TableElementBlock block : tableBlocks) {
            if(block.getElementSymbol().equals(symbol)) {
                block.setVisibility(increased);
                break;
            }
        }

        invalidate();
    }

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

    private void measureCanvas() {
        final int blockWidth = (int)(tableRect.width() / (colAmount + 3));
        final int blockHeight = tableRect.height() / (rowAmount + 3);
        tableBlockSize = Math.min(blockWidth, blockHeight);

        symbolPaint.setTextSize(tableBlockSize / 2f);
        atomicNumberPaint.setTextSize(tableBlockSize / 4f);
        weightPaint.setTextSize(tableBlockSize / 5f);
        legendPaint.setTextSize(tableBlockSize / 3f);
        setBankBlockSizeByLevel();
    }

    private void setBankBlockSizeByLevel() {
        switch(level) {
            case 1: {
                bankBlockSize = tableBlockSize;
                break;
            }
            case 2: {
                bankBlockSize = (int)(tableBlockSize * 1.5);
                break;
            }
            case 3: {
                bankBlockSize = tableBlockSize * 2;
                break;
            }
        }
    }

    private void finalizeTable() {
        tableBlocks.clear();
        bankTargets.clear();
        stateListenersList.forEach(TableStateListeners::onTableCompleted);
    }
}
