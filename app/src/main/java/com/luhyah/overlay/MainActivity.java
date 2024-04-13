package com.luhyah.overlay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    androidx.cardview.widget.CardView card0;
    Switch enable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card0 = findViewById(R.id.card_00);
        enable = findViewById(R.id.enable);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission();
            }
        });
    }

    private void permission() {
    }
}