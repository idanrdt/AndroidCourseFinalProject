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

public class PeriodicTableView extends View {

    private final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
    private final ArrayList<BankTableBlock> bankTargets = new ArrayList<>();

    private final Paint atomicNumberPaint;
    private final Paint blockPaint = new Paint();
    private final Paint blockStroke = new Paint();
    private final Paint symbolPaint;
    private final Paint weightPaint;
    private final Paint bgPaint = new Paint();

    private final Rect drawingRect = new Rect();
    private final Rect tableRect = new Rect();

    private final int rowAmount = 7;
    private final int colAmount = 18;
    private int blockSize;

    private BankTableBlock selectedBlock;

    private final Point contentOffset = new Point();

    private boolean tableReacting;

    public interface TableStateListeners {
        void onPointGained(int amountOfPoints);
        void onLifeLoss(int lifeLeft);
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
        tableReacting = true;
    }

    public void setBlocks(List<ElementTableBlock> elementsList, int width, int height, List<BankTableBlock> bankBlocks) {
        tableBlocks.clear();
        bankTargets.clear();
        tableBlocks.addAll(elementsList);
        bankTargets.addAll((bankBlocks));

        tableRect.left = 0;
        tableRect.right = width;
        tableRect.top = 0;
        tableRect.bottom = height;
        measureCanvas();

        int startBankOffset = (width / 2) - (bankBlocks.size() * (blockSize + bankBlocks.size())) / 2 ;
        int startTableOffset = (width / 2) - (colAmount * (blockSize + colAmount / 2)) / 2;

        for(ElementTableBlock block : tableBlocks) {
            block.setRow(block.getElement().period);
            block.setCol(block.getElement().group);
            block.setLocationY(blockSize * block.getCol() + startTableOffset);
            block.setLocationX(blockSize * block.getRow());
        }

        for(BankTableBlock bankBlock : bankTargets) {
            bankBlock.setLocationY((blockSize * bankBlock.getCol()) + startBankOffset);
            bankBlock.setLocationX(blockSize * bankBlock.getRow());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setTableListeners(TableStateListeners listeners) {
        this.listeners = listeners;
    }

    public void stopTableProcessing() {
        tableReacting = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int boxSize = blockSize / 2 - 1;

        for(ElementTableBlock block : tableBlocks) {
            drawingRect.right = block.getLocationY() + boxSize;
            drawingRect.left = block.getLocationY() - boxSize;
            drawingRect.bottom = block.getLocationX() + boxSize;
            drawingRect.top = block.getLocationX() - boxSize;
            canvas.drawRect(drawingRect, blockStroke);

            if(block.isisvisable()) {

                canvas.drawRect(drawingRect, blockPaint);

                canvas.drawText(block.getElement().symbol, drawingRect.left + blockSize / 2f,
                        drawingRect.bottom - (int) (blockSize / 2.8), symbolPaint);

                canvas.drawText(String.valueOf(block.getElement().atomicNumber), drawingRect.left + blockSize / 20f,
                        drawingRect.top + atomicNumberPaint.getTextSize(), atomicNumberPaint);

                canvas.drawText(String.valueOf(block.getElement().weight), drawingRect.left + blockSize / 2f,
                        drawingRect.bottom - blockSize / 20f, weightPaint);
            }
        }

        for(BankTableBlock bank : bankTargets) {
            drawingRect.right = bank.getLocationY() + boxSize;
            drawingRect.left = bank.getLocationY() - boxSize;
            drawingRect.bottom = bank.getLocationX() + boxSize;
            drawingRect.top = bank.getLocationX() - boxSize;

            canvas.drawRect(drawingRect, blockStroke);
            canvas.drawRect(drawingRect, blockPaint);
            canvas.drawText(bank.getName(), drawingRect.left + blockSize/2f, drawingRect.bottom - (int)(blockSize / 2.8), symbolPaint);
        }

        if(bankTargets.size() == 0) {
            listeners.onTableCompleted();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean continueProcessing;
        if(tableReacting) {
            int size = blockSize / 2 + 1;

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
                                    (selectedBlock.getLocationY() > elementBlock.getLocationY() - size && selectedBlock.getLocationY() < elementBlock.getLocationY() + size) &&
                                    !elementBlock.isisvisable() && elementBlock.getElement().symbol.equals(selectedBlock.getName())) {
                                elementBlock.setVisable(true);
                                bankTargets.remove(selectedBlock);
                                selectedBlock = null;
                                invalidate();
                                listeners.onPointGained(1);
                                break;
                            }
                        }

                        if (selectedBlock != null) {
                            selectedBlock.setLocationX(selectedBlock.getInitializedLocationX());
                            selectedBlock.setLocationY(selectedBlock.getInitializedLocationY());
                            selectedBlock = null;
                            invalidate();
                            listeners.onLifeLoss(1);
                        }
                        break;
                    }
                }
            }
            continueProcessing = true;
        }
        else {

            continueProcessing = false;
        }

        return continueProcessing;
    }

    private void measureCanvas() {
        final int blockWidth = (int)(tableRect.width() / (colAmount + 3));
        final int blockHeight = tableRect.height() / (rowAmount + 3);
        blockSize = Math.min(blockWidth, blockHeight);

        final int realWidth = blockSize * colAmount + blockSize;
        final int realHeight = blockSize * rowAmount + blockSize;
        contentOffset.set(Math.max(0, (tableRect.width() - realWidth) / 2),
                Math.max(0, (tableRect.height() - realHeight) / 2));

        symbolPaint.setTextSize(blockSize / 2f);
        atomicNumberPaint.setTextSize(blockSize / 4f);
        weightPaint.setTextSize(blockSize / 5f);
    }
}
