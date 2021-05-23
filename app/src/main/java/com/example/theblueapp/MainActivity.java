package com.example.theblueapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  final String TAG = "MainActivity" ;
    private ImageButton mBlueButton;

    RelativeLayout mlayout;

    private ListView DeviceView;

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
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<>();

    private BluetoothDevice mSelectedDevice;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");//Universal HC_05 modual UUID
    int[] RGBval = new int[3];



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            BluetoothDevice mDeviceb = getIntent().getExtras().getParcelable("ADDED_DEVICE");
            mDeviceList.add(mDeviceb);
        }catch (Exception x){
            Log.d(TAG,"Failed add list" + x);
        }


        if (mDeviceList.size() > 0)
        {
                DeviceView = (ListView) findViewById(R.id.mBlueList);
                Device_Adapter mAdapter = new Device_Adapter(getApplicationContext(), R.layout.bluetooth_view, mDeviceList);
                DeviceView.setAdapter(mAdapter);
                DeviceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSelectedDevice = mDeviceList.get(position);
                    }
                });
        }









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
                RGBconvertMethod(SelectedColor);


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

    public void RGBconvertMethod(int _Color)
    {
        RGBval[0] = (_Color & 0xFF0000) >> 16;
        RGBval[1] = (_Color & 0xFF00) >> 8;
        RGBval[2] = (_Color & 0xFF);

    }

    @Override//this method will be implemented as ButtonDesierd.OnClickListener(this) needs to be revied on what color v.getSolidColor() pulls
    public void onClick(View v) {
        SelectedColor = v.getSolidColor();
        mlayout.setBackgroundColor(SelectedColor);
    }
}
