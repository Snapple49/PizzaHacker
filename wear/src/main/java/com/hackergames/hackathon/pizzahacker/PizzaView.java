package com.hackergames.hackathon.pizzahacker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/**
 * Created by olive on 2016-11-19.
 */

class PizzaView extends View {
    private boolean shouldAnimate;
    private float percentage;
    public List<Drawable> pizzaParts;

    public PizzaView(Context context) {
        super(context);
        init(null, 0);
    }

    public PizzaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PizzaView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle){

    }

    public void setPercentage(float percentage){
        this.percentage = percentage;
    }

    public void setShouldAnimate(boolean value){
        this.shouldAnimate = value;
    }



    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        long millisec = System.currentTimeMillis();
        double degrees = ((millisec/1000)%60)/(2*Math.PI);
        double degrees2 =(((millisec/1000/60)/60)%24)/(2*Math.PI);
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.WHITE);
        canvas.drawLine((float)(getWidth()/2),(float)(getHeight()/2),(float)(getHeight()/2+250*Math.cos(degrees)),(float)(getWidth()/2+250*Math.sin(degrees)), linePaint);
        linePaint.setStrokeWidth(10);
        canvas.drawLine((float)(getWidth()/2),(float)(getHeight()/2),(float)(getHeight()/2+250*Math.cos(degrees2)),(float)(getWidth()/2+250*Math.sin(degrees2)), linePaint);




        System.out.println("drawOn is being called");
       // Paint p = new Paint();
        //RectF rect = new RectF(0, 0, getWidth(), getHeight());

        //p.setColor(Color.BLUE);
       pizza_status(canvas);
       // canvas.drawArc(rect, 270,180 , true, p);

        for(Drawable d:pizzaParts){
            d.draw(canvas);
        }



        //rect.set(0,0,canvas.getWidth()/2, canvas.getHeight()/2);
    }

    public void pizza_status(Canvas canvas)
    {
        if(shouldAnimate) {
            float angle = -(float)((100 - (percentage * 100)) * 3.6);
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            p.setAlpha(100);
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            //rect.bringToFront();
            canvas.drawArc(rect, 270, angle, true, p);
        }
    }



}
