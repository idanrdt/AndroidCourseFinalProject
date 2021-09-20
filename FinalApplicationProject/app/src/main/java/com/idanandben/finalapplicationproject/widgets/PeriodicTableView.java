package com.idanandben.finalapplicationproject.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.idanandben.finalapplicationproject.utilities.Element;

import java.util.ArrayList;
import java.util.List;

public class PeriodicTableView extends View {

    private ArrayList<ElementTableBlock> tableBlocks = new ArrayList<>();

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
    private int padding;

    private Point contentOffset = new Point();


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

    private void fillViewport() {
        if(mContentRect.left > 0) {
            mContentRect.right -= mContentRect.left;
            mContentRect.left = 0;
        } else if(mContentRect.right < getWidth()) {
            mContentRect.left += getWidth() - mContentRect.right;
            mContentRect.right = getWidth();
        }
        if(mContentRect.top > 0) {
            mContentRect.bottom -= mContentRect.top;
            mContentRect.top = 0;
        } else if(mContentRect.bottom < getHeight()) {
            mContentRect.top += getHeight() - mContentRect.bottom;
            mContentRect.bottom = getHeight();
        }
    }

    private void trimCanvas(int width, int height) {
        if(mContentRect.width() > width || mContentRect.height() > height) {
            final int deltaWidth = Math.max(0,
                    Math.min(mContentRect.width() - getWidth(), mContentRect.width() - width));
            final int deltaHeight = Math.max(0,
                    Math.min(mContentRect.height() - getHeight(), mContentRect.height() - height));
            final float focusX = (getWidth() / 2f - mContentRect.width()) / mContentRect.width();
            final float focusY = (getHeight() / 2f - mContentRect.top) / mContentRect.height();
            mContentRect.top += deltaHeight * focusY;
            mContentRect.bottom -= deltaHeight * (1f - focusY);
            mContentRect.left += deltaWidth * focusX;
            mContentRect.right -= deltaWidth * (1f - focusX);
        }
    }

    private void measureCanvas() {
        final int blockWidth = (int)(mContentRect.width() / (colAmount + 3));
        final int blockHeight = mContentRect.height() / (rowAmount + 3);
        blockSize = Math.min(blockWidth, blockHeight);
        padding = blockSize / 2;

        final int realWidth = blockSize * colAmount + blockSize;
        final int realHeight = blockSize * rowAmount + blockSize;
        trimCanvas(realWidth, realHeight);
        contentOffset.set(Math.max(0, (mContentRect.width() - realWidth) / 2),
                Math.max(0, (mContentRect.height() - realHeight) / 2));
        fillViewport();

        mSymbolPaint.setTextSize(blockSize / 2f);
        mNumberPaint.setTextSize(blockSize / 4f);
        mSmallTextPaint.setTextSize(blockSize / 5f);
    }

    public void setBlocks(List<ElementTableBlock> elementsList) {
        tableBlocks.clear();
        tableBlocks.addAll(elementsList);

        int rows = 0;
        int cols = 0;

        for(ElementTableBlock block : tableBlocks) {
            if(block.getElement().period > rows) {
                rows = block.getElement().period;
            }
            if(block.getElement().group > cols) {
                cols = block.getElement().group;
            }

            block.setRow(block.getElement().period);
            block.setCol(block.getElement().group);
        }

        rowAmount = rows;
        colAmount = cols;
        measureCanvas();

        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mContentRect.width() < w) {
            mContentRect.left = 0;
            mContentRect.right = w;
        }
        if(mContentRect.height() < h) {
            mContentRect.top = 0;
            mContentRect.bottom = h;
        }

        measureCanvas();
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getRight(), getBottom(), bgPaint);
        mRect.top = (int)(blockSize * 1.3) + mContentRect.top + contentOffset.y;
        mRect.left = blockSize * 3 + mContentRect.left + contentOffset.x;
        mRect.bottom = mRect.top + blockSize * 2;
        mRect.right = mRect.left + blockSize * 9;

        for(ElementTableBlock block : tableBlocks) {
            findBlockPosition(block);

            canvas.drawRect(mRect, mBlockPaint);
            canvas.drawRect(mRect, mBlockStroke);

            canvas.drawText(block.getElement().symbol, mRect.left + blockSize / 2f,
                    mRect.bottom - (int)(blockSize / 2.8), mSymbolPaint);

            canvas.drawText(String.valueOf(block.getElement().atomicNumber), mRect.left + blockSize / 20f,
                    mRect.top + mNumberPaint.getTextSize(), mNumberPaint);

            canvas.drawText(String.valueOf(block.getElement().weight), mRect.left + blockSize / 2f,
                    mRect.bottom - blockSize / 20f, mSmallTextPaint);
        }
    }

    private void findBlockPosition(@NonNull ElementTableBlock block) {
        mRect.right =
                (block.getCol() * blockSize + mContentRect.left + contentOffset.x + padding) - 1;
        mRect.bottom =
                (block.getRow() * blockSize + mContentRect.top + contentOffset.y + padding) - 1;
        mRect.left = mRect.right - blockSize + 1;
        mRect.top = mRect.bottom - blockSize + 1;

        final int number = block.getElement().atomicNumber;
    }
}
