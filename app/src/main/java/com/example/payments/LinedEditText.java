package com.example.payments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        int iHeight = ((View)this.getParent()).getHeight();
        int iLineHeight = getLineHeight();
        int iNumberOfLines = iHeight/iLineHeight;

        Rect r = mRect;
        Paint paint = mPaint;

        int baseline = getLineBounds(0, r);

        for(int i = 0; i < iNumberOfLines; i++){
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += iLineHeight;
        }

        super.onDraw(canvas);
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
    }
}
