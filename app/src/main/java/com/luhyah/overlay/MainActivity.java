//  ****                            ****                ****            ****                ****            ****                ****
//  ****                            ****                ****            ****                ****            ****                ****
//  ****                            ****                ****            ****************                **************
//  ****                            ****                ****            ****************                           *********
//  ************            ****************            ****                ****           ***                    ****
//  ************            ****************            ****                ****            ****************

package com.luhyah.overlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private androidx.cardview.widget.CardView card0;
    private androidx.cardview.widget.CardView card2, card2b;
    private androidx.cardview.widget.CardView card3;
    private Switch enable;
    private Spinner overlayTpeSpinner, fontSizeSpinner, fontSpinner;
    private EditText timeText, overlayText;
    private TextView start, save, pausePlay, end;
    private SeekBar xSeekBar, ySeekBar;
    private final String SharedPref = "com.luhyah.overlay.preference_file_key";

    private int fontSizeVal, overlayTypeVal, fontVal;
    private boolean enabledVal;
    private final String overlayTextFileName = "OVERLAYTEXT";
    @SuppressLint("ResourceType")
    private int reload = 1;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;

    //    Context context = this;
    private SharedPreferences VALUES;

    private TextView OT, F, FS, P, M, X, Y, xVal, yVal;

    private int xBarValue, yBarValue;
    private CountDownTimer countDowITimer;
    private long pausedTime;
    private boolean isTimerActive;
    private WindowManager windowManager;
    private boolean isThereOverlay;

    private  TextView OVERLAY;
    private  String timeLeft;
    private Typeface abel, amita, comfortaa, damion;

    private  androidx.cardview.widget.CardView overlayCard;
    private int ocH, ocW;
    private float defValueX, defValueY;

    private  TextView startCT, pausePlayCT, endCT;
    private Handler handler;

    private int initTime = 0;
    private  String timeElapsed;

    /*--------------------------------------onCREATE---------------------------------------------------*/
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        VALUES = getSharedPreferences (SharedPref , MODE_PRIVATE);
        /*----------------------------------------------------------------*/

        isTimerActive = true;
        initialization ();
        LayoutInflater inflater = (LayoutInflater) getSystemService (LAYOUT_INFLATER_SERVICE);
        View overlayView = inflater.inflate (R.layout.overlayout , null);
        OVERLAY = overlayView.findViewById (R.id.overlaidText);
        overlayCard = overlayView.findViewById (R.id.overlayCard);
        /*----------------------------------------------------------------*/

        /*==========================================================================*/
        checkNLoadPreferences ();
        /*==========================================================================*/

        if (checkIfPermissionIsGranted ()) {
            card0.setVisibility (View.GONE);
        } else {
            enable.setChecked (false);
            enable.setEnabled (false);
            disabled ();

        }

        card0.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                grantPermission ();
            }
        });

        enabledVal = enable.isChecked ();
        if (enable.isChecked ()) {
            notDisabled ();
            showOverlay (overlayView);

        } else {
            disabled ();
            isThereOverlay = false;

        }
        enable.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton , boolean b) {
                enabledVal = enable.isChecked ();
                if (b) {
                    notDisabled ();
                    showOverlay (overlayView);
                } else {
                    disabled ();
                   removeOverlay (overlayView);
                }
            }
        });

        overlayTypeVal = overlayTpeSpinner.getSelectedItemPosition ();
        measureCard ();
        overlayTpeSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {

            @Override
            public void onItemSelected(AdapterView<?> adapterView , View view , int i , long l) {

                if (i == 0) {
                    card2.setVisibility (View.VISIBLE);
                    card3.setVisibility (View.GONE);
                    card2b.setVisibility (View.GONE);
                    measureCard ();
                    overlayCard.setTranslationY (0);
                    overlayCard.setTranslationX (0);
                    xSeekBar.setProgress (0);
                    ySeekBar.setProgress (0);
                    if(pausedTime != 0){OVERLAY.setText (timeLeft);}
                    else {OVERLAY.setText ("00:00:00");}

                    if(initTime!= 0){
                        pauseCTimer ();
                        pausePlayCT.setText ("\t Play \t");
                    }
                }
                else if (i == 1) {
                    card2b.setVisibility (View.VISIBLE);
                    card2.setVisibility (View.GONE);
                    card3.setVisibility (View.GONE);
                    measureCard ();
                    overlayCard.setTranslationY (0);
                    overlayCard.setTranslationX (0);
                    xSeekBar.setProgress (0);
                    ySeekBar.setProgress (0);
                    if(initTime != 0){OVERLAY.setText (timeElapsed);}
                    else {OVERLAY.setText ("00:00:00");}

                    if (countDowITimer != null) {
                        pauseTimer ();
                        pausePlay.setText ("\t Play \t");
                    }

                } else if (i == 2){

                    card3.setVisibility (View.VISIBLE);
                    card2.setVisibility (View.GONE);
                    card2b.setVisibility (View.GONE);
                    measureCard ();
                    overlayCard.setTranslationY (0);
                    overlayCard.setTranslationX (0);
                    xSeekBar.setProgress (0);
                    ySeekBar.setProgress (0);
                    if (countDowITimer != null) {
                        pauseTimer ();
                        pausePlay.setText ("\t Play \t");
                    }
                    if(initTime != 0){
                        pauseCTimer ();
                        pausePlayCT.setText ("\t Play \t");
                    }
                    if (readText () != null) {
                        OVERLAY.setText (readText ());
                    } else {
                        OVERLAY.setText ("Type Something in the APP");
                    }

                }
                overlayTypeVal = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }


        });

        fontSizeVal = fontSizeSpinner.getSelectedItemPosition ();
        fontSizeSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView , View view , int i , long l) {
                fontSizeVal = i;
                //Switch Statement  to read font sizes and implement necessary changes
                switch (fontSizeVal) {
                    case 0:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 12);
                        measureCard ();
                        break;
                    case 1:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 14);
                        measureCard ();
                        break;
                    case 2:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 16);
                        measureCard ();
                        break;
                    case 3:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 18);
                        measureCard ();
                        break;
                    case 4:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 20);
                        measureCard ();
                        break;
                    case 5:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 22);
                        measureCard ();
                        break;
                    case 6:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 24);
                        measureCard ();
                        break;
                    default:
                        OVERLAY.setTextSize (TypedValue.COMPLEX_UNIT_SP, 15);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fontVal = fontSpinner.getSelectedItemPosition ();
        fontSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView , View view , int i , long l) {
                fontVal = i;
                //Switch Statement to read Fonts and implement neccessary changes
                switch (fontVal) {
                    case 0:
                        OVERLAY.setTypeface (abel);
                        measureCard ();
                        break;
                    case 1:
                        OVERLAY.setTypeface (amita);
                        measureCard ();
                        break;
                    case 2:
                        OVERLAY.setTypeface (comfortaa);
                        measureCard ();
                        break;
                    case 3:
                        OVERLAY.setTypeface (damion);
                        measureCard ();
                    default:
                        //

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (readText () != null) {
            overlayText.setText (readText ());
        } else {
            overlayText.setText ("Input Text to Overlay");
        }

        xBarValue = xSeekBar.getProgress ();
        xSeekBar.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged(SeekBar seekBar , int value , boolean b) {
                xBarValue = xSeekBar.getProgress ();
                xVal.setText (value + "%");
                double[] X = getScreenSize ();
                float x = (float)X[0];
                float tst = x-(ocW );
                    if(((float)value/100 * x) >=tst ) {
                        overlayCard.setTranslationX (tst);
                        defValueX = tst;
                    }
                    else{
                        overlayCard.setTranslationX ((float)value/100 * x);
                        defValueX = (float)value/100 * x;
                    }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        yBarValue = ySeekBar.getProgress ();
        ySeekBar.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged(SeekBar seekBar , int i , boolean b) {
                yBarValue = ySeekBar.getProgress ();
                yVal.setText (i + "%");
                double[] Y = getScreenSize ();
                float y = (float)Y[1];
                float tst = y-(ocH );
                if(((float)i/100 * y) >=tst ) {
                    overlayCard.setTranslationY (tst);
                    defValueY = tst;
                }
                else{
                    overlayCard.setTranslationY ((float)i/100 * y);
                    defValueY = (float)i/100 * y;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        start.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                int min = Integer.parseInt (timeText.getText ().toString ());
                startTimer (min);
            }
        });
        end.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                endTimer ();
            }
        });
        pausePlay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (pausePlay.getText ().toString ().equals ("\t Pause \t")) {
                    pausePlay.setText ("\t Play \t");
                    pauseTimer ();
                } else {
                    pausePlay.setText ("\t Pause \t");
                    resumeTimer ();
                }
            }
        });

        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                saveText ();
                OVERLAY.setText (overlayText.getText ().toString ());

            }
        });

        startCT.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startCTimer ();
            }
        });
        pausePlayCT.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (pausePlayCT.getText ().toString ().equals ("\t Pause \t")) {
                    pausePlayCT.setText ("\t Play \t");
                    pauseCTimer ();
                } else {
                    pausePlayCT.setText ("\t Pause \t");
                    playCT ();
                }
            }
        });
        endCT.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                endCT ();
            }
        });
    }

///////////////////////////////////////////////////////END OF onCREATE////////////////////////////////////////////////////////
    //Write Overlay Text to Memory
    public void saveText() {
        String overlayTextString = overlayText.getText ().toString ();
        fileOutputStream = null;
        if (overlayTextString.length () > 2) {
            try {
                fileOutputStream = openFileOutput (overlayTextFileName , MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException (e);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.write (overlayTextString.getBytes ());
                        Toast.makeText (this , "SAVED" , Toast.LENGTH_SHORT).show ();
                        fileOutputStream.close ();
                    } catch (IOException e) {
                        throw new RuntimeException (e);
                    }
                }
            }
        } else {
            Toast.makeText (this , "TEXT TOO SHORT" , Toast.LENGTH_SHORT).show ();
        }
    }

    // Read OverlayText from Memory
    public String readText() {
        fileInputStream = null;
        try {
            fileInputStream = openFileInput (overlayTextFileName);
            InputStreamReader inputStreamReader = new InputStreamReader (fileInputStream);
            BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder ();
            String placeHolder;
            while ((placeHolder = bufferedReader.readLine ()) != null) {
                stringBuilder.append (placeHolder).append ("\n");
            }
            return stringBuilder.toString ();
        } catch (Exception e) {
            return "TEXT TO OVERLAY";
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close ();
                } catch (IOException e) {
                    throw new RuntimeException (e);
                }
            }
        }
    }

    private static int REQUEST_CODE = 1;

    private void grantPermission() {
        Intent intent =
                new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION , Uri.parse ("package:" + getPackageName ()));
        startActivityForResult (intent , REQUEST_CODE);
    }

    private boolean checkIfPermissionIsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (Settings.canDrawOverlays (this)) {
                return true;
            }
        }
        return false;
    }

    private void disabled() {
        overlayTpeSpinner.setEnabled (false);
        fontSpinner.setEnabled (false);
        fontSizeSpinner.setEnabled (false);
        timeText.setEnabled (false);
        overlayText.setEnabled (false);
        xSeekBar.setEnabled (false);
        ySeekBar.setEnabled (false);
    }

    private void notDisabled() {
        overlayTpeSpinner.setEnabled (true);
        fontSpinner.setEnabled (true);
        fontSizeSpinner.setEnabled (true);
        timeText.setEnabled (true);
        overlayText.setEnabled (true);
        xSeekBar.setEnabled (true);
        ySeekBar.setEnabled (true);

    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (checkIfPermissionIsGranted ()) {
            card0.setVisibility (View.GONE);
            enable.setEnabled (true);
        } else {
            card0.setVisibility (View.VISIBLE);
            enable.setChecked (false);
            enable.setEnabled (false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause ();
        savePreferences ();
    }

    private void initialization() {
        card0 = findViewById (R.id.card_00);
        card2 = findViewById (R.id.card_02);
        card3 = findViewById (R.id.card_03);
        enable = findViewById (R.id.enable);
        overlayTpeSpinner = findViewById (R.id.overlayTypeSpinner);
        fontSpinner = findViewById (R.id.fontSpinner);
        fontSizeSpinner = findViewById (R.id.fontSizeSpinner);
        timeText = findViewById (R.id.timeText);
        start = findViewById (R.id.start);
        overlayText = findViewById (R.id.overlayText);
        save = findViewById (R.id.save);
        xSeekBar = findViewById (R.id.xSeekBar);
        ySeekBar = findViewById (R.id.ySeekBar);
        OT = findViewById (R.id.OT);
        F = findViewById (R.id.F);
        FS = findViewById (R.id.FS);
        P = findViewById (R.id.P);
        M = findViewById (R.id.M);
        X = findViewById (R.id.X);
        Y = findViewById (R.id.Y);
        xVal = findViewById (R.id.xV);
        yVal = findViewById (R.id.yV);
        pausePlay = findViewById (R.id.pause_play);
        end = findViewById (R.id.end);
        ///////////////////////////////////SPINNER ADAPTERS////////////////////////////////////////////////////////
        ArrayAdapter<CharSequence> overlayType =
                ArrayAdapter.createFromResource (this , R.array.overlayType , android.R.layout.simple_spinner_item);
        overlayType.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        overlayTpeSpinner.setAdapter (overlayType);

        ArrayAdapter<CharSequence> font =
                ArrayAdapter.createFromResource (this , R.array.fonts , android.R.layout.simple_spinner_item);
        font.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter (font);

        ArrayAdapter<CharSequence> fontSize =
                ArrayAdapter.createFromResource (this , R.array.fontSize , android.R.layout.simple_spinner_item);
        fontSize.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter (fontSize);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        abel = ResourcesCompat.getFont (this, R.font.abel);
        amita = ResourcesCompat.getFont (this, R.font.amita_bold);
        comfortaa = ResourcesCompat.getFont (this, R.font.comfortaa_bold);
        damion = ResourcesCompat.getFont (this, R.font.damion);

        startCT = findViewById (R.id.startCT);
        pausePlayCT = findViewById (R.id.pause_playCT);
        endCT = findViewById (R.id.endCT);
        handler = new Handler ();
        card2b =findViewById (R.id.card_02b);
    }

    private void checkNLoadPreferences() {
        Map<String, ?> allValues = VALUES.getAll ();
        if (allValues.isEmpty ()) {
            disabled ();
        } else {
            ySeekBar.setProgress (VALUES.getInt ("Y_BAR_VALUE" , 0));
            xSeekBar.setProgress (VALUES.getInt ("X_BAR_VALUE" , 0));
            overlayTpeSpinner.setSelection (VALUES.getInt ("OVERLAY_TYPE_VALUE" , 0));
            fontSpinner.setSelection (VALUES.getInt ("FONT_VALUE" , 0));
            fontSizeSpinner.setSelection (VALUES.getInt ("FONT_SIZE_VALUE" , 0));
            boolean temp = VALUES.getBoolean ("ENABLE_VALUE" , true);
            overlayCard.setTranslationX (VALUES.getFloat ("DEFAULT_XPOS", 0));
            overlayCard.setTranslationY (VALUES.getFloat ("DEFAULT_YPOS", 0));
            if (temp) {
                enable.setChecked (true);
            } else {
                enable.setChecked (false);
            }
            xVal.setText (VALUES.getInt ("X_BAR_VALUE" , 0) + "%");
            yVal.setText (VALUES.getInt ("Y_BAR_VALUE" , 0) + "%");
        }
    }

    private void savePreferences() {
        SharedPreferences.Editor valuesEditor = VALUES.edit ();
        valuesEditor.putInt ("Y_BAR_VALUE" , yBarValue);
        valuesEditor.putInt ("X_BAR_VALUE" , xBarValue);
        valuesEditor.putBoolean ("ENABLE_VALUE" , enabledVal);
        valuesEditor.putInt ("OVERLAY_TYPE_VALUE" , overlayTypeVal);
        valuesEditor.putInt ("FONT_SIZE_VALUE" , fontSizeVal);
        valuesEditor.putInt ("FONT_VALUE" , fontVal);
        valuesEditor.putFloat ("DEFAULT_XPOS", defValueX);
        valuesEditor.putFloat ("DEFAULT_YPOS", defValueY);
        valuesEditor.apply ();
    }

    private void startTimer(long min) {
        countDowITimer = new CountDownTimer (min * 60 * 1000 , 1000) {
            @Override
            public void onTick(long l) {
                pausedTime = l;
                long hours = l / (60 * 60 * 1000);
                long minutes = l / (60 * 1000) % 60;
                long seconds = (l / 1000) % 60;

             timeLeft = String.format (Locale.getDefault () , "%02d:%02d:%02d" , hours , minutes , seconds);
               OVERLAY.setText (timeLeft);
            }

            @Override
            public void onFinish() {
                OVERLAY.setText ("00:00:00");
                isTimerActive = false;
            }
        };
        countDowITimer.start ();
        isTimerActive = true;
    }

    private void pauseTimer() {
        countDowITimer.cancel ();
        isTimerActive = false;
    }

    private void resumeTimer() {
        if (! isTimerActive) {
            countDowITimer = new CountDownTimer (pausedTime , 1000) {
                @Override
                public void onTick(long l) {
                    pausedTime = l;
                    long hours = l / (60 * 60 * 1000);
                    long minutes = l / (60 * 1000) % 60;
                    long seconds = (l / 1000) % 60;
                   timeLeft = String.format (Locale.getDefault () , "%02d:%02d:%02d" , hours , minutes , seconds);
                   OVERLAY.setText (timeLeft);
                }

                @Override
                public void onFinish() {
                    OVERLAY.setText ("00:00:00");
                    isTimerActive = false;
                }
            };
            countDowITimer.start ();
            isTimerActive = true;
        }
    }

    private void endTimer() {
        countDowITimer.cancel ();
        pausedTime = 0;
        OVERLAY.setText ("00:00:00");
    }

    private double[] getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics ();
        getWindowManager ().getDefaultDisplay ().getMetrics (displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        double[] placeHolder = {width, height};
        return placeHolder;
    }

    private void showOverlay(View view) {

        if(!isThereOverlay) {
            windowManager = (WindowManager) getSystemService (WINDOW_SERVICE);


                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams (
                        WindowManager.LayoutParams.MATCH_PARENT ,
                        WindowManager.LayoutParams.MATCH_PARENT ,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
                        PixelFormat.TRANSLUCENT);

                windowManager.addView (view , layoutParams);
                isThereOverlay = true;

        }
    }

    private void removeOverlay(View view) {
        windowManager = (WindowManager) getSystemService (WINDOW_SERVICE);
        if(windowManager != null){
        windowManager.removeView (view);
        isThereOverlay = false;
        }
    }

    private  void measureCard(){
        overlayCard.getViewTreeObserver ().addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener () {
            @Override
            public void onGlobalLayout() {
                overlayCard.getViewTreeObserver ().removeOnGlobalLayoutListener (this);
                ocH = overlayCard.getHeight ();
                ocW = overlayCard.getWidth ();
            }
        });
    }

    private void startCTimer(){
        handler.post (new Runnable () {
            @Override
            public void run() {
                initTime++;
               int hours = initTime/3600;
                int minutes = (initTime/3600)%60;
               int seconds = initTime%60;

                timeElapsed = String.format (Locale.getDefault () , "%02d:%02d:%02d" , hours , minutes , seconds);
                OVERLAY.setText (timeElapsed);
                handler.postDelayed (this, 1000);
            }
        });
    }
    private  void pauseCTimer(){
            handler.removeCallbacksAndMessages (null);
    }
    private void playCT(){
        handler.post (new Runnable () {
            @Override
            public void run() {
                initTime++;
                int hours = initTime/3600;
                int minutes = (initTime/3600)%60;
                int seconds = initTime%60;

                timeElapsed = String.format (Locale.getDefault () , "%02d:%02d:%02d" , hours , minutes , seconds);
                OVERLAY.setText (timeElapsed);
                handler.postDelayed (this, 1000);
            }
        });

    }
    private void endCT(){
        handler.removeCallbacksAndMessages (null);
        initTime= 0;
        timeElapsed ="00:00:00";
        OVERLAY.setText (timeElapsed);
    }
}