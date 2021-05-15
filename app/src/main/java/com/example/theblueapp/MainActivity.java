package com.example.theblueapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBlueButton;

    RelativeLayout mlayout;

    private TextView mOpasitytext;//Displayed Percent
    private ProgressBar mOpasityBar;// Barr that will alow me to retreve values
    private SeekBar mOseekbar;// The slider Barr for the opacity\brightness


    private Button ColorWbutton;// Button that will call color wheel
    private int SelectedColor;


    private ImageButton OnOffButton;//Power button in the center

    //region 5 Preset Buttons (continue Later)
    private Button mPresetColor1;
    private Button mPresetColor2;
    private Button mPresetColor3;
    private Button mPresetColor4;
    private Button mPresetColor5;
    //endregion





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBlueButton = (ImageButton) findViewById(R.id.AddBlueDeviceButton);
        mBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBluetoothActivity();
            }
        });

        //region Progress Bar for Opasity
        mOpasitytext = (TextView) findViewById(R.id.TextViewOpasity);
        mOpasityBar = (ProgressBar) findViewById(R.id.Opasitybar);
        mOseekbar = (SeekBar) findViewById(R.id.OpasitySeekBar);

        mOseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mOpasityBar.setProgress(progress);
                mOpasitytext.setText(""+progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //endregion



        //region ColorWheel Button
        mlayout = (RelativeLayout) findViewById(R.id.Home);
        SelectedColor = ContextCompat.getColor(MainActivity.this, R.color.design_default_color_primary);
        ColorWbutton = (Button) findViewById(R.id.ColorWheelOpen);
        ColorWbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        //endregion



        //region On Off Button capabilities (know: revisit for bluetooth ques)
        OnOffButton = (ImageButton) findViewById(R.id.On_Off);
        OnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisableOpacityBar(mOpasityBar,mOseekbar,mOpasitytext);
            }
        });
        //endregion



        //region ColorPreset Buttons
        mPresetColor1 = (Button) findViewById(R.id.ColorPreset1);
        mPresetColor2 = (Button) findViewById(R.id.ColorPreset2);
        mPresetColor3 = (Button) findViewById(R.id.ColorPreset3);
        mPresetColor4 = (Button) findViewById(R.id.ColorPreset4);
        mPresetColor5 = (Button) findViewById(R.id.ColorPreset5);


        mPresetColor1.setOnClickListener(this);
        mPresetColor2.setOnClickListener(this);
        mPresetColor3.setOnClickListener(this);
        mPresetColor4.setOnClickListener(this);
        mPresetColor5.setOnClickListener(this);
        //endregion


    }
    public void openColorPicker()
    {
        AmbilWarnaDialog colorWheel = new AmbilWarnaDialog(this, SelectedColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                SelectedColor = color;
                mlayout.setBackgroundColor(SelectedColor);
            }
        });
        colorWheel.show();
    }
    public void DisableOpacityBar(ProgressBar _Pbar, SeekBar _sbar, TextView _Opasitytext)
    {
        if (_Pbar.getVisibility() == View.VISIBLE) {
            _Pbar.setVisibility(View.GONE);
            _sbar.setVisibility(View.GONE);
            _Opasitytext.setVisibility(View.GONE);
        }else {
            _Pbar.setVisibility(View.VISIBLE);
            _sbar.setVisibility(View.VISIBLE);
            _Opasitytext.setVisibility(View.VISIBLE);
        }
    }
    public void OpenBluetoothActivity()
    {
        Intent mopenBlue = new Intent(MainActivity.this, BlueToothActivity.class);
        startActivity(mopenBlue);

    }

    @Override//this method will be implemented as ButtonDesierd.OnClickListener(this) needs to be revied on what color v.getSolidColor() pulls
    public void onClick(View v) {
        SelectedColor = v.getSolidColor();
        mlayout.setBackgroundColor(SelectedColor);
    }
}