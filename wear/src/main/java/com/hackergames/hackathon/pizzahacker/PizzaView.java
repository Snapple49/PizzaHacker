package com.hackergames.hackathon.pizzahacker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;

import org.w3c.dom.Attr;

import java.util.Iterator;
import java.util.List;

/**
 * Created by olive on 2016-11-19.
 */

class PizzaView extends View {

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



    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
       // Paint p = new Paint();
        //RectF rect = new RectF(0, 0, getWidth(), getHeight());

        //p.setColor(Color.BLUE);
       pizza_status((float)0.23,canvas);
       // canvas.drawArc(rect, 270,180 , true, p);
        /*
        for(Drawable d:pizzaParts){
            d.draw(canvas);
        }
        */


        //rect.set(0,0,canvas.getWidth()/2, canvas.getHeight()/2);
    }

    public void pizza_status(float percentage,Canvas canvas)
    {
        float angle = (float) ((percentage * 100)*3.6);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setAlpha(100);
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        //rect.bringToFront();
        canvas.drawArc(rect, 270,angle, true, p);
    }



}
