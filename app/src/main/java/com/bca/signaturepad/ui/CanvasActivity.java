package com.bca.signaturepad.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.signaturepad.R;
import com.bca.signaturepad.controller.CanvasController;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class CanvasActivity extends AppCompatActivity {

    private ImageButton btnCircle, btnRect, btnSign, btnUndo, btnRedo;
    private Slider valueSlider;
    private TextInputEditText etRadius;
    private CanvasController controller;
    private CanvasController.DrawingCanvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_canvas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        controller = new CanvasController(this);
        canvas = new CanvasController.DrawingCanvas(this);
        findViews();
        registerEvents();
        initDefaultBehavior();
    }

    private void initDefaultBehavior() {

        btnSign.setSelected(true); // Default selection
        enableSlider(false); // Radius slider for circle shape only
        valueSlider.setValue(CanvasController.DEF_RADIUS);
        enableUndo(false);
        enableRedo(false);
    }

    private void registerEvents() {

        btnSign.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            btnCircle.setSelected(false);
            btnRect.setSelected(false);
            if (v.isSelected()) controller.setShape(CanvasController.SIGNATURE);
            else controller.setShape(CanvasController.NONE);
            enableSlider(!v.isSelected());
        });

        btnRect.setOnClickListener(v -> {
            v.setSelected(!v.isSelected()); // Toggle behavior
            btnCircle.setSelected(false);
            btnSign.setSelected(false);
            if (v.isSelected())  controller.setShape(CanvasController.RECT);
            else controller.setShape(CanvasController.NONE);
            enableSlider(!v.isSelected());
        });

        btnCircle.setOnClickListener(v -> {
            v.setSelected(!v.isSelected()); // Toggle behavior
            btnSign.setSelected(false);
            btnRect.setSelected(false);
            if (v.isSelected())  controller.setShape(CanvasController.CIRCLE);
            else controller.setShape(CanvasController.NONE);
            enableSlider(v.isSelected());
        });

        btnUndo.setOnClickListener(v -> canvas.undo());

        btnRedo.setOnClickListener( v -> canvas.redo());

        etRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value = Integer.parseInt(s.toString());
                    if (value < 1 || value > 10) {
                        etRadius.setText(String.valueOf(CanvasController.DEF_RADIUS));
                        return;
                    }
                    controller.setRadius(value);
                    valueSlider.setValue(value);
                } catch (Exception e) {
                    controller.setRadius(CanvasController.DEF_RADIUS);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });

        valueSlider.addOnChangeListener((slider, value, fromUser) -> {
            controller.setRadius((int) value);
            etRadius.setText(String.valueOf((int) value));
        });
    }

    public void enableUndo(boolean value) {
        btnUndo.setEnabled(value);
        if (value) btnUndo.setImageDrawable(getDrawable(R.drawable.undo));
        else btnUndo.setImageDrawable(getDrawable(R.drawable.undo_disabled));
    }

    public void enableRedo(boolean value) {
        btnRedo.setEnabled(value);
        if (value) btnRedo.setImageDrawable(getDrawable(R.drawable.redo));
        else btnRedo.setImageDrawable(getDrawable(R.drawable.redo_disabled));
    }

    private void enableSlider(boolean value) {

        valueSlider.setEnabled(value);
        etRadius.setEnabled(value);
    }

    private void findViews() {

        btnCircle = findViewById(R.id.btnCircle);
        btnRect = findViewById(R.id.btnRect);
        btnSign = findViewById(R.id.btnSign);
        btnUndo = findViewById(R.id.btnUndo);
        btnRedo = findViewById(R.id.btnRedo);
        etRadius = findViewById(R.id.etRadius);
        valueSlider = findViewById(R.id.valueSlider);
        LinearLayout layoutCanvas = findViewById(R.id.layoutCanvas);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutCanvas.setLayoutParams(params);
        layoutCanvas.addView(canvas);
    }

}
