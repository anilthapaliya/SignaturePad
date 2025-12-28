package com.bca.signaturepad.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bca.signaturepad.R;
import com.bca.signaturepad.controller.CanvasController;
import com.google.android.material.textfield.TextInputEditText;

public class CanvasActivity extends AppCompatActivity {

    private ImageButton btnCircle, btnRect;
    private TextInputEditText etRadius;
    private CanvasController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_canvas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        controller = new CanvasController(this);
        findViews();
        registerEvents();
    }

    private void registerEvents() {

        btnCircle.setOnClickListener(v -> {
            v.setSelected(!v.isSelected()); // Toggle behavior
            btnRect.setSelected(false);
        });

        btnRect.setOnClickListener(v -> {
            v.setSelected(!v.isSelected()); // Toggle behavior
            btnCircle.setSelected(false);
        });
    }

    private void findViews() {

        btnCircle = findViewById(R.id.btnCircle);
        btnRect = findViewById(R.id.btnRect);
        etRadius = findViewById(R.id.etRadius);
    }

}
