package com.bca.signaturepad.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bca.signaturepad.interfaces.DrawingShape;

public class CircleShape implements DrawingShape {

    private final float x;
    private final float y;
    private final int radius;
    private final Paint paint;

    public CircleShape(float x, float y, int radius, Paint paint) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawCircle(x, y, radius, paint);
    }

}
