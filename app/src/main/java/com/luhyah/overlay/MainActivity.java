package com.luhyah.overlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private androidx.cardview.widget.CardView card0;
    private androidx.cardview.widget.CardView card2;
    private androidx.cardview.widget.CardView card3;
    private Switch enable;
    private Spinner overlayTpeSpinner,fontSizeSpinner,fontSpinner;
    private EditText timeText, overlayText;
    private TextView start, save;
    private SeekBar xSeekBar, ySeekBar;
    @SuppressLint("ResourceType")


private int reload = 1;

    private TextView OT, F, FS, P, M, X, Y, xVal, yVal;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card0 = findViewById(R.id.card_00);
        card2 = findViewById(R.id.card_02);
        card3 = findViewById(R.id.card_03);

        enable = findViewById(R.id.enable);
        overlayTpeSpinner = findViewById(R.id.overlayTypeSpinner);
        fontSpinner = findViewById(R.id.fontSpinner);
        fontSizeSpinner = findViewById(R.id.fontSizeSpinner);
        timeText = findViewById(R.id.timeText);
        start = findViewById(R.id.start);
        overlayText = findViewById(R.id.overlayText);
        save = findViewById(R.id.save);
        xSeekBar = findViewById(R.id.xSeekBar);
        ySeekBar = findViewById(R.id.ySeekBar);

        OT = findViewById(R.id.OT);
        F = findViewById(R.id.F);
        FS = findViewById(R.id.FS);
        P = findViewById(R.id.P);
        M = findViewById(R.id.M);
        X = findViewById(R.id.X);
        Y = findViewById(R.id.Y);
        xVal = findViewById(R.id.xV);
        yVal = findViewById(R.id.yV);

        overlayTpeSpinner.setEnabled(false);
        fontSpinner.setEnabled(false);
        fontSizeSpinner.setEnabled(false);
        timeText.setEnabled(false);
        overlayText.setEnabled(false);
        xSeekBar.setEnabled(false);
        ySeekBar.setEnabled(false);

        if(checkIfPermissionIsGranted()){
            card0.setVisibility(View.GONE);
        }
        else{
                enable.setChecked(false);
            enable.setEnabled(false);
                overlayTpeSpinner.setEnabled(false);
                fontSpinner.setEnabled(false);
                fontSizeSpinner.setEnabled(false);
                timeText.setEnabled(false);
                overlayText.setEnabled(false);
                xSeekBar.setEnabled(false);
                ySeekBar.setEnabled(false);

           /*     save.setTextColor(Color.rgb(129,129,133));

                start.setTextColor(Color.rgb(129,129,133));
                OT.setTextColor(Color.rgb(129,129,133));
                F.setTextColor(Color.rgb(129,129,133));
                FS.setTextColor(Color.rgb(129,129,133));
                P.setTextColor(Color.rgb(129,129,133));
                M.setTextColor(Color.rgb(129,129,133));
                X.setTextColor(Color.rgb(129,129,133));
                Y.setTextColor(Color.rgb(129,129,133));
                xVal.setTextColor(Color.rgb(129,129,133));
                yVal.setTextColor(Color.rgb(129,129,133));*/

        }

        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantPermission();
            }
        });


     enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    overlayTpeSpinner.setEnabled(false);
                    fontSpinner.setEnabled(false);
                    fontSizeSpinner.setEnabled(false);
                    timeText.setEnabled(false);
                    overlayText.setEnabled(false);
                    xSeekBar.setEnabled(false);
                    ySeekBar.setEnabled(false);
                }
                else {
                    overlayTpeSpinner.setEnabled(true);
                    fontSpinner.setEnabled(true);
                    fontSizeSpinner.setEnabled(true);
                    timeText.setEnabled(true);
                    overlayText.setEnabled(true);
                    xSeekBar.setEnabled(true);
                    ySeekBar.setEnabled(true);

                }
         }
     });

        ArrayAdapter<CharSequence> overlayType = ArrayAdapter.createFromResource(this,R.array.overlayType, android.R.layout.simple_spinner_item);
        overlayType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overlayTpeSpinner.setAdapter(overlayType);

        ArrayAdapter<CharSequence> font = ArrayAdapter.createFromResource(this,R.array.fonts, android.R.layout.simple_spinner_item);
        font.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(font);

        ArrayAdapter<CharSequence> fontSize = ArrayAdapter.createFromResource(this,R.array.fontSize, android.R.layout.simple_spinner_item);
        fontSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(fontSize);


    if(overlayTpeSpinner.getSelectedItem().toString() == "TIMER"){
        Log.d("SPINNER", "onCreate: SPINNER TEXT");
    }

    }

    private static int REQUEST_CODE = 1;

    private void grantPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);

    }
    private boolean checkIfPermissionIsGranted(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            if(Settings.canDrawOverlays(this)){
                return  true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(checkIfPermissionIsGranted()){
            card0.setVisibility(View.GONE);
            enable.setEnabled(true);
        }
        else {
            card0.setVisibility(View.VISIBLE);
            enable.setChecked(false);
            enable.setEnabled(false);
            overlayTpeSpinner.setEnabled(false);
            fontSpinner.setEnabled(false);
            fontSizeSpinner.setEnabled(false);
            timeText.setEnabled(false);
            overlayText.setEnabled(false);
            xSeekBar.setEnabled(false);
            ySeekBar.setEnabled(false);

        }
    }
}