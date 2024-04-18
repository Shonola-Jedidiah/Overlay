//  ****                            ****                ****            ****                ****            ****                ****
//  ****                            ****                ****            ****                ****            ****                ****
//  ****                            ****                ****            ****************                **************
//  ****                            ****                ****            ****************                           *********
//  ************            ****************            ****                ****           ***                    ****
//  ************            ****************            ****                ****            ****************

package com.luhyah.overlay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
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


public class MainActivity extends AppCompatActivity {

    private androidx.cardview.widget.CardView card0;
    private androidx.cardview.widget.CardView card2;
    private androidx.cardview.widget.CardView card3;
    private Switch enable;
    private Spinner overlayTpeSpinner, fontSizeSpinner, fontSpinner;
    private EditText timeText, overlayText;
    private TextView start, save;
    private SeekBar xSeekBar, ySeekBar;
    private String SharedPref = "com.luhyah.overlay.preference_file_key";

    private int fontSizeVal , enabledVal, overlayTypeVal, fontVal;
    private String overlayTextFileName = "OVERLAYTEXT";
    @SuppressLint("ResourceType")
    private int reload = 1;
  private   FileInputStream fileInputStream;
  private   FileOutputStream fileOutputStream;

    //    Context context = this;
    private SharedPreferences VALUES ;

    private TextView OT, F, FS, P, M, X, Y, xVal, yVal;

    //=============================SHARED PREFERENCE VALUES HOLDERS=================================//
    private int xBarValue, yBarValue;

// ================================================================================================//

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VALUES = getSharedPreferences(SharedPref, MODE_PRIVATE);
        /*==========================================================================*/
        initialization();
      checkNLoadPreferences();
        /*==========================================================================*/

        if (checkIfPermissionIsGranted()) {
            card0.setVisibility(View.GONE);
        } else {
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
                if (! b) {
                    overlayTpeSpinner.setEnabled(false);
                    fontSpinner.setEnabled(false);
                    fontSizeSpinner.setEnabled(false);
                    timeText.setEnabled(false);
                    overlayText.setEnabled(false);
                    xSeekBar.setEnabled(false);
                    ySeekBar.setEnabled(false);
                    enabledVal = 0;
                } else {
                    overlayTpeSpinner.setEnabled(true);
                    fontSpinner.setEnabled(true);
                    fontSizeSpinner.setEnabled(true);
                    timeText.setEnabled(true);
                    overlayText.setEnabled(true);
                    xSeekBar.setEnabled(true);
                    ySeekBar.setEnabled(true);
                    enabledVal = 1;
                }
            }
        });

        ///////////////////////////////////SPINNER ADAPTERS////////////////////////////////////////////////////////
        ArrayAdapter<CharSequence> overlayType = ArrayAdapter.createFromResource(this, R.array.overlayType, android.R.layout.simple_spinner_item);
        overlayType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overlayTpeSpinner.setAdapter(overlayType);

        ArrayAdapter<CharSequence> font = ArrayAdapter.createFromResource(this, R.array.fonts, android.R.layout.simple_spinner_item);
        font.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(font);

        ArrayAdapter<CharSequence> fontSize = ArrayAdapter.createFromResource(this, R.array.fontSize, android.R.layout.simple_spinner_item);
        fontSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(fontSize);
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        overlayTpeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overlayTypeVal = i;
                if (i == 0) {
                    card2.setVisibility(View.VISIBLE);
                    card3.setVisibility(View.GONE);
                } else if(i == 1){
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
                fontSizeVal = i;
                //Switch Statement  to read font sizes and implement necessary changes
                switch (fontSizeVal) {
                    case 0:
                        //6
                        break;
                    case 1:
                        //7
                        break;
                    case 2:
                        //8
                        break;
                    case 3:
                        //9
                        break;
                    case 4:
                        //10
                        break;
                    case 5:
                        //11
                        break;
                    case 6:
                        //12
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
                fontVal = i;
                //Switch Statement to read Fonts and implement neccessary changes
                switch (fontVal) {
                    case 0:
                        //
                        break;
                    case 1:
                        //
                        break;
                    case 2:
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
        }else {
            overlayText.setText("Input Text to Overlay");
        }


        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                xBarValue = value;
                xVal.setText(value +"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yBarValue = i;
                yVal.setText(i +"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    //Write Overlay Text to Memory
    public void save(View view) {
        String overlayTextString = overlayText.getText().toString();

         fileOutputStream = null;
        if (overlayTextString.length() > 2) {
            try {
                fileOutputStream = openFileOutput(overlayTextFileName, MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.write(overlayTextString.getBytes());
                        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
                        fileOutputStream.close();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            Toast.makeText(this, "TEXT TOO SHORT", Toast.LENGTH_SHORT).show();
        }
    }

    public String readText() {
        fileInputStream  = null;

        try {
             fileInputStream = openFileInput(overlayTextFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String placeHolder;
            while ((placeHolder = bufferedReader.readLine()) != null) {
                stringBuilder.append(placeHolder).append("\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
           return "TEXT TO OVERLAY";
        } finally {
            if (fileInputStream != null) {
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

    private boolean checkIfPermissionIsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (Settings.canDrawOverlays(this)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkIfPermissionIsGranted()) {
            card0.setVisibility(View.GONE);
            enable.setEnabled(true);
        } else {
            card0.setVisibility(View.VISIBLE);
            enable.setChecked(false);
            enable.setEnabled(false);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    private void initialization() {
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
    }

    private void checkNLoadPreferences() {
        Map<String, ?> allValues = VALUES.getAll();
        if (allValues.isEmpty()) {
            overlayTpeSpinner.setEnabled(false);
            fontSpinner.setEnabled(false);
            fontSizeSpinner.setEnabled(false);
            timeText.setEnabled(false);
            overlayText.setEnabled(false);
            xSeekBar.setEnabled(false);
            ySeekBar.setEnabled(false);
        } else {
                ySeekBar.setProgress(VALUES.getInt("Y_BAR_VALUE", 0));
                xSeekBar.setProgress( VALUES.getInt("X_BAR_VALUE", 0));
               overlayTpeSpinner.setSelection((VALUES.getInt("OVERLAY_TYPE_VALUE", 0)));
                fontSpinner.setSelection(VALUES.getInt("FONT_VALUE", 0));
                fontSizeSpinner.setSelection(VALUES.getInt("FONT_SIZE_VALUE", 0));

                if(VALUES.getInt("ENABLE_VALUE", 0)==0){
                    enable.setChecked(false);
                }else {
                    enable.setChecked(true);
                }
        }
    }

    private void savePreferences() {
         SharedPreferences.Editor valuesEditor = VALUES.edit();
        valuesEditor.putInt("Y_BAR_VALUE", yBarValue);
        valuesEditor.putInt("X_BAR_VALUE", xBarValue);
        valuesEditor.putInt("ENABLE_VALUE", enabledVal);
        valuesEditor.putInt("OVERLAY_TYPE_VALUE", overlayTypeVal);
        valuesEditor.putInt("FONT_SIZE_VALUE", fontSizeVal);
        valuesEditor.putInt("FONT_VALUE", fontVal);
        valuesEditor.apply();
    }
}