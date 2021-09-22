package com.idanandben.finalapplicationproject.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class PeriodicTableView extends View {

    private final ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();
    //private final ArrayList<String> bankTargets = new ArrayList<>();

    private final Paint mNumberPaint;
    private final Paint mBlockPaint = new Paint();
    private final Paint mBlockStroke = new Paint();
    private final Paint mSymbolPaint;
    private final Paint mSmallTextPaint;
    private final Paint bgPaint = new Paint();

    private final Rect mRect = new Rect();
    private final Rect mContentRect = new Rect();

    private int rowAmount;
    private int colAmount;
    private int blockSize;
    private int clickedBlockIndex;

    private Point contentOffset = new Point();

    public interface ElementBankDragListener {
        void onBankElementDrag(ElementTableBlock item);
    }

    private ElementBankDragListener bankElementDragListener;

    public PeriodicTableView(Context context) {
        this(context, null, 0);
    }

    public PeriodicTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PeriodicTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mNumberPaint = new Paint();
        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setColor(Color.BLACK);

        mSymbolPaint = new Paint(mNumberPaint);
        mSymbolPaint.setTextAlign(Paint.Align.CENTER);

        mSmallTextPaint = new Paint(mSymbolPaint);
        mSmallTextPaint.setSubpixelText(true);

        bgPaint.setColor(Color.WHITE);
        mBlockPaint.setColor(Color.WHITE);
        mBlockStroke.setStyle(Paint.Style.STROKE);
        mBlockStroke.setColor(Color.BLACK);
        mBlockStroke.setStrokeWidth(2);
    }

    public void setBlocks(List<ElementTableBlock> elementsList, DisplayMetrics metrics) {
        tableBlocks.clear();
        tableBlocks.addAll(elementsList);

        rowAmount = 7;
        colAmount = 18;
        mContentRect.left = 0;
        mContentRect.right = metrics.widthPixels;
        mContentRect.top = 0;
        mContentRect.bottom = metrics.heightPixels;
        measureCanvas();

        for(ElementTableBlock block : tableBlocks) {
            block.setRow(block.getElement().period);
            block.setCol(block.getElement().group);
            block.setLocationY(blockSize * block.getCol());
            block.setLocationX(blockSize * block.getRow());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(ElementTableBlock block : tableBlocks) {
            mRect.right = block.getLocationY() + blockSize / 2 - 1;
            mRect.left = block.getLocationY() - blockSize / 2 - 1;
            mRect.bottom = block.getLocationX() + blockSize / 2 - 1;
            mRect.top = block.getLocationX() - blockSize / 2 - 1;

            canvas.drawRect(mRect, mBlockPaint);
            canvas.drawRect(mRect, mBlockStroke);

            canvas.drawText(block.getElement().symbol, mRect.left + blockSize / 2f,
                    mRect.bottom - (int)(blockSize / 2.8), mSymbolPaint);

            canvas.drawText(String.valueOf(block.getElement().atomicNumber), mRect.left + blockSize / 20f,
                    mRect.top + mNumberPaint.getTextSize(), mNumberPaint);

            canvas.drawText(String.valueOf(block.getElement().weight), mRect.left + blockSize / 2f,
                    mRect.bottom - blockSize / 20f, mSmallTextPaint);
        }

        mRect.bottom = mRect.bottom + (int)(blockSize * 2);
        mRect.top = mRect.bottom - blockSize -1;

/*        for(String str : bankTargets) {
            canvas.drawRect(mRect, mBlockStroke);
            canvas.drawText(str,mRect.left + blockSize/2f,mRect.bottom - (int)(blockSize / 2.8), mSymbolPaint);
            mRect.right += blockSize;
            mRect.left = mRect.right - blockSize;
        }*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int size = blockSize / 2 + 1;
                int blockIndex = 0;
                for(ElementTableBlock block : tableBlocks) {
                    if(((int)event.getX() > block.getLocationY() - size && (int)event.getX() < block.getLocationY() + size) &&
                            (int)event.getY() >= block.getLocationX() - size && (int)event.getY() <= block.getLocationX() + size) {
                        clickedBlockIndex = blockIndex;
                        break;
                    }
                    blockIndex++;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(clickedBlockIndex >= 0 && clickedBlockIndex <= tableBlocks.size()) {
                    tableBlocks.get(clickedBlockIndex).setLocationY((int) event.getX());
                    tableBlocks.get(clickedBlockIndex).setLocationX((int) event.getY());
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if(clickedBlockIndex >= 0 && clickedBlockIndex <= tableBlocks.size()) {
                    ElementTableBlock block = tableBlocks.get(clickedBlockIndex);
                    block.setLocationX(block.getInitializedLocationX());
                    block.setLocationY(block.getInitializedLocationY());
                    clickedBlockIndex = -1;
                    invalidate();
                }
                break;
            }
        }
            return true;
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }


    private void measureCanvas() {
        final int blockWidth = (int)(mContentRect.width() / (colAmount + 3));
        final int blockHeight = mContentRect.height() / (rowAmount + 3);
        blockSize = Math.min(blockWidth, blockHeight);

        final int realWidth = blockSize * colAmount + blockSize;
        final int realHeight = blockSize * rowAmount + blockSize;
        contentOffset.set(Math.max(0, (mContentRect.width() - realWidth) / 2),
                Math.max(0, (mContentRect.height() - realHeight) / 2));

        mSymbolPaint.setTextSize(blockSize / 2f);
        mNumberPaint.setTextSize(blockSize / 4f);
        mSmallTextPaint.setTextSize(blockSize / 5f);
    }
}
