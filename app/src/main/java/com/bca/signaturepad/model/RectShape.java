package com.bca.signaturepad.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bca.signaturepad.interfaces.DrawingShape;

public class RectShape implements DrawingShape {

    private final RectF rect = new RectF();
    private final Paint paint;

    public RectShape(float x1, float y1, float x2, float y2, Paint paint) {
        this.rect.set(x1, y1, x2, y2);
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawRect(rect, paint);
    }

}
