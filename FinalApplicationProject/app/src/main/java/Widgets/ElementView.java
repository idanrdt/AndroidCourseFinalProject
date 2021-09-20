package Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ElementView extends View {

    private int number;
    private String symbol;

    public ElementView(Context context, @Nullable AttributeSet attrs, int number, String symbol) {
        super(context, attrs);
        this.number = number;
        this.symbol = symbol;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawElementNumber(canvas, number);
        drawElementSymbol(canvas, symbol);
    }

    private void drawElementNumber(Canvas canvas, int number) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText(String.valueOf(number),50,50,paint);
    }

    private void drawElementSymbol(Canvas canvas, String symbol) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(120);
        canvas.drawText(symbol,(int)canvas.getWidth()/2-(int)paint.measureText((symbol))/2,150,paint);
    }
}
