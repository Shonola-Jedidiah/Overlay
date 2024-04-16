package com.luhyah.overlay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {

    private androidx.cardview.widget.CardView card0;
    private androidx.cardview.widget.CardView card2;
    private androidx.cardview.widget.CardView card3;
    private Switch enable;
    private Spinner overlayTpeSpinner,fontSizeSpinner,fontSpinner;
    private EditText timeText, overlayText;
    private TextView start, save;
    private SeekBar xSeekBar, ySeekBar;

    private String overlayVal;
    private  int fontSizeVal;
    private String fontVal;
    private String overlayTextFileName = "OVERLAYTEXT";
    @SuppressLint("ResourceType")
private int reload = 1;

    private SharedPreferences VALUES = getSharedPreferences("savedValues", MODE_PRIVATE);
    private TextView OT, F, FS, P, M, X, Y, xVal, yVal;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializtion();
        checkPreferences();

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

      overlayTpeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              overlayVal = overlayType.getItem(i).toString();
              if(Objects.equals(overlayVal, "TIMER")){
                  card2.setVisibility(View.VISIBLE);
                  card3.setVisibility(View.GONE);
              }
              else  {
                  card3.setVisibility(View.VISIBLE);
                  card2.setVisibility(View.GONE);
              }
          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
          }
      });

      fontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              fontSizeVal = Integer.parseInt(fontSize.getItem(i).toString());
              //Switch Statement  to read font sizes and implement necessary changes
              switch (fontSizeVal){
                  case 6:
                      //
                        break;
                  case 7:
                      //
                      break;
                  case 8:
                      //
                      break;
                  case 9:
                      //
                      break;
                  case 10:
                      //
                      break;
                  case 11:
                      //
                      break;
                  case 12:
                      //
                      break;
                  default:
                      //
              }

          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });

      fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              fontVal = font.getItem(i).toString();
              //Switch Statement to read Fonts and implement neccessary changes
              switch (fontVal){
                  case "COMFORTAA":
                      //
                      break;
                  case "AMITA":
                      //
                      break;
                  case "CALIBRI":
                      break;
                  default:
                      //

              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });


        if (readText() != null) {
            overlayText.setText(readText());
        }

    }

//Write Overlay Text to Memory
  public void save(View view){
        String overlayTextString = overlayText.getText().toString();

      FileOutputStream fileOutputStream = null;
        if(overlayTextString.length() > 2) {
            try {
                 fileOutputStream = openFileOutput(overlayTextFileName, MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            finally{
                if (fileOutputStream != null){
                    try {
                        fileOutputStream.write(overlayTextString.getBytes());
                        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
                        fileOutputStream.close();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "TEXT TOO SHORT", Toast.LENGTH_SHORT).show();
        }
  }

  public String readText(){
      FileInputStream fileInputStream = null;

      try {
          fileInputStream = openFileInput(overlayTextFileName);
          InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
          StringBuilder stringBuilder = new StringBuilder();
          String placeHolder;
          while ((placeHolder = bufferedReader.readLine()) != null) {
              stringBuilder.append(placeHolder).append("\n");
          }
          return  stringBuilder.toString();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
   finally {
          if(fileInputStream != null){
              try {
                  fileInputStream.close();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          }
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

    private void initializtion(){
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
    }

    private void checkPreferences(){
        Map<String, ?> allValues = VALUES.getAll();
        if(allValues.isEmpty()){
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