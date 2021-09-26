package com.idanandben.finalapplicationproject.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeriodicTableView extends View {

    private final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
    private final ArrayList<BankTableBlock> bankTargets = new ArrayList<>();

    private final Paint atomicNumberPaint;
    private final Paint blockPaint = new Paint();
    private final Paint blockStroke = new Paint();
    private final Paint symbolPaint;
    private final Paint weightPaint;
    private final Paint bgPaint = new Paint();

    private final Rect drawingCursor = new Rect();
    private final Rect tableRect = new Rect();

    private final int rowAmount = 7;
    private final int colAmount = 18;
    private int tableBlockSize;
    private int bankBlockSize;

    private BankTableBlock selectedBlock;

    private final Point contentOffset = new Point();

    private boolean isTableWorking;

    public interface TableStateListeners {
        void onCorrectElementPlaced();
        void onWrongElementPlaced();
        void onTableCompleted();
    }

    private TableStateListeners listeners;

    public PeriodicTableView(Context context) {
        this(context, null, 0);
    }

    public PeriodicTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PeriodicTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        atomicNumberPaint = new Paint();
        atomicNumberPaint.setAntiAlias(true);
        atomicNumberPaint.setColor(Color.BLACK);

        symbolPaint = new Paint(atomicNumberPaint);
        symbolPaint.setTextAlign(Paint.Align.CENTER);

        weightPaint = new Paint(symbolPaint);
        weightPaint.setSubpixelText(true);

        bgPaint.setColor(Color.WHITE);
        blockPaint.setColor(Color.WHITE);
        blockStroke.setStyle(Paint.Style.STROKE);
        blockStroke.setColor(Color.BLACK);
        blockStroke.setStrokeWidth(2);
    }

    public void initializeTable(List<ElementTableBlock> elementsList, int screenWidth, int screenHeight, List<BankTableBlock> bankBlocks) {
        tableBlocks.clear();
        bankTargets.clear();
        tableBlocks.addAll(elementsList);
        bankTargets.addAll((bankBlocks));

        tableRect.left = 0;
        tableRect.right = screenWidth;
        tableRect.top = 0;
        tableRect.bottom = screenHeight;
        measureCanvas();

        int startBankOffset = (screenWidth / 2) - (bankBlocks.size() * (bankBlockSize + bankBlocks.size())) / 2 ;
        int startTableOffset = (screenWidth / 2) - (colAmount * (tableBlockSize + colAmount / 2)) / 2;

        for(ElementTableBlock block : tableBlocks) {
            block.setRow(block.getElement().period);
            block.setCol(block.getElement().group);
            block.setLocationY(tableBlockSize * block.getCol() + startTableOffset);
            block.setLocationX(tableBlockSize * block.getRow());
        }

        for(BankTableBlock bankBlock : bankTargets) {
            bankBlock.setLocationY((bankBlockSize * bankBlock.getCol()) + startBankOffset);
            bankBlock.setLocationX(tableBlockSize * bankBlock.getRow());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setTableListeners(TableStateListeners listeners) {
        this.listeners = listeners;
    }

    public void stopTableProcessing() {
        isTableWorking = false;
    }

    public void startTableProcessing() { isTableWorking = true; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLevel1Table(canvas);
    }

    private void drawLevel1Table(Canvas canvas) {
        int boxSize = tableBlockSize / 2 - 1;

        for(ElementTableBlock block : tableBlocks) {
            drawingCursor.right = block.getLocationY() + boxSize;
            drawingCursor.left = block.getLocationY() - boxSize;
            drawingCursor.bottom = block.getLocationX() + boxSize;
            drawingCursor.top = block.getLocationX() - boxSize;
            canvas.drawRect(drawingCursor, blockStroke);

            if(block.getVisibility()) {
                blockPaint.setColor(block.getColor());

                canvas.drawRect(drawingCursor, blockPaint);

                canvas.drawText(block.getElement().symbol, drawingCursor.left + tableBlockSize / 2f,
                        drawingCursor.bottom - (int) (tableBlockSize / 2.8), symbolPaint);

                if(!(block.getElement().symbol.contains("*"))) {
                    canvas.drawText(String.valueOf(block.getElement().atomicNumber), drawingCursor.left + tableBlockSize / 20f,
                            drawingCursor.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

                    canvas.drawText(String.valueOf(block.getElement().weight), drawingCursor.left + tableBlockSize / 2f,
                            drawingCursor.bottom - tableBlockSize / 20f, weightPaint);
                }
            }
        }

        boxSize = bankBlockSize / 2 - 1;

        for(BankTableBlock bank : bankTargets) {

            drawingCursor.right = bank.getLocationY() + boxSize;
            drawingCursor.left = bank.getLocationY() - boxSize;
            drawingCursor.bottom = bank.getLocationX() + boxSize;
            drawingCursor.top = bank.getLocationX() - boxSize;

            blockPaint.setColor(bank.getColor());

            canvas.drawRect(drawingCursor, blockStroke);
            canvas.drawRect(drawingCursor, blockPaint);
            canvas.drawText(bank.getName(), drawingCursor.left + bankBlockSize /2f, drawingCursor.bottom - (int)(bankBlockSize / 3.5), symbolPaint);
        }

        if(bankTargets.size() == 0) {
            listeners.onTableCompleted();
        }
    }

    private void drawLevel2Table() {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTableWorking) {
            dragBankElement(event);
        }
        return true;
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

                    for (ElementTableBlock elementBlock : tableBlocks) {
                        if ((selectedBlock.getLocationX() > elementBlock.getLocationX() - size && selectedBlock.getLocationX() < elementBlock.getLocationX() + size) &&
                                (selectedBlock.getLocationY() > elementBlock.getLocationY() - size && selectedBlock.getLocationY() < elementBlock.getLocationY() + size)) {
                            if(!elementBlock.getVisibility() && elementBlock.getElement().symbol.equals(selectedBlock.getName())) {
                                elementBlock.setVisibility(true);
                                bankTargets.remove(selectedBlock);
                                selectedBlock = null;
                                invalidate();
                                listeners.onCorrectElementPlaced();
                                break;
                            } else {
                                listeners.onWrongElementPlaced();
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

    private void clickTableElement() {

    }

    private void measureCanvas() {
        final int blockWidth = (int)(tableRect.width() / (colAmount + 3));
        final int blockHeight = tableRect.height() / (rowAmount + 3);
        tableBlockSize = Math.min(blockWidth, blockHeight);
        bankBlockSize = (int)(tableRect.width() / (colAmount + 9));

        final int realWidth = tableBlockSize * colAmount + tableBlockSize;
        final int realHeight = tableBlockSize * rowAmount + tableBlockSize;
        contentOffset.set(Math.max(0, (tableRect.width() - realWidth) / 2),
                Math.max(0, (tableRect.height() - realHeight) / 2));

        symbolPaint.setTextSize(tableBlockSize / 2f);
        atomicNumberPaint.setTextSize(tableBlockSize / 4f);
        weightPaint.setTextSize(tableBlockSize / 5f);
    }
}
