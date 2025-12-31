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
import com.bca.signaturepad.ui.CanvasActivity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class CanvasController {

    private static CanvasActivity view;
    private static int shape, radius;
    public final static int NONE = 0, RECT = 1, CIRCLE = 2, SIGNATURE = 3;
    public final static int DEF_RADIUS = 4;
    public final static int RADIUS_MULTIPLIER = 15;

    public CanvasController(CanvasActivity view) {

        CanvasController.view = view;
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
        private Path signPath;
        private Paint paint;
        private Paint signPaint;
        private final ArrayDeque<DrawingShape> undoQueue = new ArrayDeque<>();
        private final ArrayDeque<DrawingShape> redoQueue = new ArrayDeque<>();

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

        private void addShape(DrawingShape shape) {

            undoQueue.push(shape);
            redoQueue.clear();
            view.enableUndo(true);
            view.enableRedo(false);
            invalidate();
        }

        public void undo() {

            if (!undoQueue.isEmpty()) {
                redoQueue.push(undoQueue.pop());
                invalidate();
                view.enableUndo(!undoQueue.isEmpty());
                view.enableRedo(!redoQueue.isEmpty());
            }
        }

        public void redo() {

            if (!redoQueue.isEmpty()) {
                undoQueue.push(redoQueue.pop());
                invalidate();
                view.enableRedo(!redoQueue.isEmpty());
                view.enableUndo(!undoQueue.isEmpty());
            }
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            for (DrawingShape c : undoQueue) {
                c.draw(canvas);
            }

            if (shape == SIGNATURE && signPath != null)
                canvas.drawPath(signPath, signPaint);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            x = event.getX();
            y = event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (shape == SIGNATURE) {
                        setSignatureStyle();
                        signPath = new Path();
                        signPath.moveTo(x, y);
                        return true;
                    } else {
                        setPaintStyle();
                        x = event.getX();
                        y = event.getY();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    signPath.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    if (shape == RECT)
                        addShape(new RectShape(x, y, x + 100f, y + 100f, paint));
                    else if (shape == CIRCLE)
                        addShape(new CircleShape(x, y, radius * RADIUS_MULTIPLIER, paint));
                    else if (shape == SIGNATURE) {
                        addShape(new PathShape(signPath, signPaint));
                        signPath = null;
                    }

                    break;
            }

            invalidate();

            return true;
        }

    }

}
