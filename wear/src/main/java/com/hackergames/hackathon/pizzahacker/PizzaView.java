package com.hackergames.hackathon.pizzahacker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;

/**
 * Created by olive on 2016-11-19.
 */

class PizzaView extends View {

    class ToggleBoolean {
        boolean bool;

        public ToggleBoolean(boolean bool) {
            this.bool = bool;
        }

        public void toggle() {
            bool = !bool;
        }
    }

    final Drawable basic_pizza = getResources().getDrawable(R.drawable.basic_pizza, null);
    final Drawable pizza_base = getResources().getDrawable(R.drawable.pizza_base, null);
    final Drawable pizza_sauce = getResources().getDrawable(R.drawable.pizza_sauce, null);
    final Drawable pizza_cheese = getResources().getDrawable(R.drawable.pizza_cheese, null);
    final Drawable toppings_pepperoni = getResources().getDrawable(R.drawable.topping_pepperoni, null);
    final Drawable toppings_fish = getResources().getDrawable(R.drawable.topping_fish, null);
    private boolean shouldAnimate;
    private float percentage;
    public LinkedHashMap<Drawable, ToggleBoolean> pizzaParts;

    public PizzaView(Context context) {
        super(context);
        init(null, 0);
    }

    public PizzaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PizzaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void setBoundsPercentage(Drawable drawable, int percent) {
        drawable.setBounds((getWidth() * percent) / 100, (getHeight() * percent) / 100, (getWidth() * (100 - percent)) / 100, (getHeight() * (100 - percent)) / 100);
    }

    private void init(AttributeSet attrs, int defStyle) {
        pizzaParts = new LinkedHashMap<Drawable, ToggleBoolean>();
        pizzaParts.put(basic_pizza, new ToggleBoolean(true));
        pizzaParts.put(pizza_base, new ToggleBoolean(false));
        pizzaParts.put(pizza_sauce, new ToggleBoolean(false));
        pizzaParts.put(pizza_cheese, new ToggleBoolean(false));
        pizzaParts.put(toppings_pepperoni, new ToggleBoolean(false));
        pizzaParts.put(toppings_fish, new ToggleBoolean(false));

        setBoundsPercentage(basic_pizza, 0);
        setBoundsPercentage(pizza_base, 0);
        setBoundsPercentage(pizza_sauce, 4);
        setBoundsPercentage(pizza_cheese, 6);
        setBoundsPercentage(toppings_pepperoni, 45);
        setBoundsPercentage(toppings_fish, 45);


    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setShouldAnimate(boolean value) {
        this.shouldAnimate = value;
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        long millisec = System.currentTimeMillis();
        double degrees = ((millisec / 1000) % 60) / (2 * Math.PI);
        double degrees2 = (((millisec / 1000 / 60) / 60) % 24) / (2 * Math.PI);
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.WHITE);
        canvas.drawLine((float) (getWidth() / 2), (float) (getHeight() / 2), (float) (getWidth() / 2 + 250 * Math.cos(degrees)), (float) (getHeight() / 2 + 250 * Math.sin(degrees)), linePaint);
        linePaint.setStrokeWidth(10);
        canvas.drawLine((float) (getWidth() / 2), (float) (getHeight() / 2), (float) (getWidth() / 2 + 250 * Math.cos(degrees2)), (float) (getHeight() / 2 + 250 * Math.sin(degrees2)), linePaint);

        setBoundsPercentage(basic_pizza, 5);
        setBoundsPercentage(pizza_base, 5);
        setBoundsPercentage(pizza_sauce, 9);
        setBoundsPercentage(pizza_cheese, 11);
        setBoundsPercentage(toppings_pepperoni, 45);
        setBoundsPercentage(toppings_fish, 45);


        for (Map.Entry<Drawable, ToggleBoolean> e : pizzaParts.entrySet()) {
            if (e.getValue().bool) {
                e.getKey().draw(canvas);
            }
        }

        pizza_status(canvas);
        System.out.println("drawOn is being called");
    }

    public void pizza_status(Canvas canvas) {
        if (shouldAnimate) {
            float angle = -(float) ((100 - (percentage * 100)) * 3.6);
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            p.setAlpha(100);
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            //rect.bringToFront();
            canvas.drawArc(rect, 270, angle, true, p);
        }
    }


}
