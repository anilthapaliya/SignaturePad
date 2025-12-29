package com.bca.signaturepad.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.bca.signaturepad.interfaces.DrawingShape;

public class PathShape implements DrawingShape {

    private final Path path;
    private final Paint paint;

    public PathShape(Path path, Paint paint) {
        this.path = path;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawPath(path, paint);
    }

}
