package com.example.theblueapp;


import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  final String TAG = "MainActivity" ;
    private ImageButton mBlueButton;


    byte[] bytes;



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
    private final ArrayList<BluetoothDevice> mDeviceList = new ArrayList<>();

    private BluetoothDevice mSelectedDevice;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BTConnectionService mConectionservice = new BTConnectionService(this);


    int[] RGBval = new int[3];
    int onoff ;
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onoff = 0;





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
                if (onoff == 1)
                {
                    onoff = 0;
                }
                else
                {
                    onoff = 1;
                }
                SendBTMessage(onoff,4);

                DisableOpacityBar(mOpasityBar,mOseekbar,mOpasitytext);

            }
        });
        //endregion



        //region ColorPreset Buttons
        mPresetColor1 = (Button) findViewById(R.id.ColorPreset1);
        mPresetColor1.setBackground(new PaintDrawable(Color.RED));
        mPresetColor2 = (Button) findViewById(R.id.ColorPreset2);
        mPresetColor2.setBackground(new PaintDrawable(Color.GREEN));
        mPresetColor3 = (Button) findViewById(R.id.ColorPreset3);
        mPresetColor3.setBackground(new PaintDrawable(Color.BLUE));
        mPresetColor4 = (Button) findViewById(R.id.ColorPreset4);
        mPresetColor4.setBackground(new PaintDrawable(Color.MAGENTA));
        mPresetColor5 = (Button) findViewById(R.id.ColorPreset5);
        mPresetColor5.setBackground(new PaintDrawable(Color.YELLOW));


        mPresetColor1.setOnClickListener(this);
        mPresetColor2.setOnClickListener(this);
        mPresetColor3.setOnClickListener(this);
        mPresetColor4.setOnClickListener(this);
        mPresetColor5.setOnClickListener(this);
        //endregion


    }
    public void startBTServices()
    {


        startConnection(mSelectedDevice,MY_UUID_INSECURE);
    }


    private void startBTConnection(BluetoothDevice mSelectedDevice, UUID myUuidInsecure) {
        mConectionservice.startClient(mSelectedDevice,myUuidInsecure);
    }

    private void startConnection(BluetoothDevice device, UUID uuid)
    {
        startBTConnection(device,uuid);
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
                SendBTMessage(SelectedColor,4);




                //mConectionservice.write(bytes);

            }
        });
        colorWheel.show();
    }
    public void SendBTMessage(int _message, int _buffSize)
    {
        ByteBuffer Buffer = ByteBuffer.allocate(_buffSize);
        Buffer.putInt(_message);
        bytes = Buffer.array();

        BluetoothAdapter aAdapt = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice arduino = aAdapt.getRemoteDevice(mSelectedDevice.getAddress());
        BluetoothSocket mSocket = null;




        do {
            try {
                mSocket = arduino.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                mSocket.connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }while (!mSocket.isConnected());



        try {

            OutputStream OutPut =mSocket.getOutputStream();
            OutPut.write(bytes);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            mSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void ReciveBTMessage(int _messageContainer)
    {

        BluetoothAdapter aAdapt = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice arduino = aAdapt.getRemoteDevice(mSelectedDevice.getAddress());
        BluetoothSocket mSocket = null;




        do {
            try {
                mSocket = arduino.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                mSocket.connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }while (!mSocket.isConnected());



        InputStream inPut = null;
        try {
            inPut = mSocket.getInputStream();
            DataInputStream message = new DataInputStream(inPut);
            Log.d(TAG, "Arduino responce: "+ message.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        try {
            mSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


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
    public void onClick(View v) {//has to be made for each button individualy

        PaintDrawable ColorDrawable = (PaintDrawable) v.getBackground();
        SelectedColor = ColorDrawable.getPaint().getColor();
        SendBTMessage(SelectedColor,4);
    }
}
