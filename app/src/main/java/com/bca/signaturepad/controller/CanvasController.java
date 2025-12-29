package com.bca.signaturepad.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.bca.signaturepad.interfaces.DrawingShape;
import com.bca.signaturepad.model.CircleShape;
import com.bca.signaturepad.model.PathShape;
import com.bca.signaturepad.model.RectShape;

import java.util.ArrayList;
import java.util.List;

public class CanvasController {

    private static int shape, radius;
    public final static int NONE = 0, RECT = 1, CIRCLE = 2, SIGNATURE = 3;
    public final static int DEF_RADIUS = 4;
    public final static int RADIUS_MULTIPLIER = 15;

    public CanvasController() {
        shape = SIGNATURE;
        radius = DEF_RADIUS;
    }

    public void setShape(int value) {
        shape = value;
    }

    public void setRadius(int value) {
        radius = value;
    }

    public static class DrawingCanvas extends View implements View.OnTouchListener {

        float x, y;
        private final RectF rect = new RectF();
        private final Path signPath = new Path();
        private Paint paint;
        private Paint signPaint;
        private List<DrawingShape> drawnShapes;

        public DrawingCanvas(Context context) {

            super(context);
            setOnTouchListener(this);
            init();
        }

        private void init() {

            paint = new Paint();
            paint.setAntiAlias(true);
            signPaint = new Paint();
            signPaint.setAntiAlias(true);
            drawnShapes = new ArrayList<>();
        }

        private void setSignatureStyle() {

            signPaint.setStyle(Paint.Style.STROKE);
            signPaint.setStrokeWidth(8f);
            signPaint.setStrokeCap(Paint.Cap.ROUND);
            signPaint.setStrokeJoin(Paint.Join.ROUND);
        }

        private void setPaintStyle() {

            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            if (shape == SIGNATURE) setSignatureStyle();
            else setPaintStyle();

            switch (shape) {
                case RECT:
                    drawnShapes.add(new RectShape(x, y, x + 100f, y + 100f, paint));
                    break;
                case CIRCLE:
                    int actualRadius = radius * RADIUS_MULTIPLIER;
                    drawnShapes.add(new CircleShape(x, y, actualRadius, paint));
                    break;
                case SIGNATURE:
                    drawnShapes.add(new PathShape(signPath, signPaint));
                    break;
            }

            for (DrawingShape c : drawnShapes) {
                c.draw(canvas);
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            x = event.getX();
            y = event.getY();

            if (shape == SIGNATURE) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        signPath.moveTo(x, y);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        signPath.lineTo(x, y);
                        break;
                }
            }

            invalidate();

            return true;
        }

    }

}
