package com.cll.sample.downloadnet.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import java.text.DecimalFormat;

/**
 * Created by cll on 2018/1/20.
 */

public class ProgressBarLayout extends ProgressBar {

    private String test1;
    private String test2;
    public ProgressBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void setTest1(String test1,String test2) {
//        Log.w("tag","test setTest1 test1 = "+test1 + "  test2 = " + test2);
        this.test1 = test1;
        this.test2 = test2;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect mRect = new Rect();
        String text;
        if (Float.valueOf(test1) <= 1){
            if (Float.valueOf(test2) <= 1){
                text =convert(test1) +"kb/"+convert(test2)  + "kb";
            }else{
                text =convert(test1)  +"kb/"+test2  + "M";
            }
        }else{
            text =test1+"M/"+test2 + "M";
        }
        mPaint.getTextBounds(text,0,text.length(),mRect);
        int x = (getWidth() / 2) - mRect.centerX();
        int y = (getHeight() / 2) - mRect.centerY();
        canvas.drawText(text,x,y,mPaint);
    }

    private String convert(String number){
        String a = new DecimalFormat("0.00").format((float)(Float.valueOf(number) * 1024));
        Log.w("tag","test Poster qqqqqqqqq  a= "+a+"  Float.valueOf(number) = "+Float.valueOf(number));
        return a;
    }

    private float getTextHei() {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float)Math.ceil(fm.descent - fm.top) + 2;
    }
    private Paint mPaint;
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
    }
}
