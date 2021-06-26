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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Color_Dialog.Color_DialogListener {
    private  final String TAG = "MainActivity" ;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //region Main Assets
    private int SelectedColor;
    private String stringSelctedColor;
    int onoff;
    byte[] bytes;
    int[] RGBval = new int[3];
    //endregion

    //region XML Assets
    RelativeLayout mlayout;

    private ListView DeviceView;

    private TextView mOpasitytext;//Displayed Percent

    private ProgressBar mOpasityBar;// Barr that will alow me to retreve values

    private SeekBar mOseekbar;// The slider Barr for the opacity\brightness

    private ImageButton mListActivitybtn;

    private ImageButton OnOffButton;//Power button in the center

    private Button ColorWbutton;// Button that will call color wheel

    //region 5 Preset Buttons (continue Later)
    private Button mPresetColor1;
    private Button mPresetColor2;
    private Button mPresetColor3;
    private Button mPresetColor4;
    private Button mPresetColor5;
    //endregion

    private ImageButton mBlueButton;
    //endregion

    //region BT Assets
    private BluetoothDevice mSelectedDevice;
    private final ArrayList<BluetoothDevice> mDeviceList = new ArrayList<>();
    //endregion


    private Group_Page GroupListactivity = new Group_Page();


    //region On CreateMethod @Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onoff = 0;

        //region GetsDeviceFromBTActivity
        try
        {
            BluetoothDevice mDeviceb = getIntent().getExtras().getParcelable("ADDED_DEVICE");
            mDeviceList.add(mDeviceb);
        }catch (Exception x){
            Log.d(TAG,"Failed add list" + x);
        }
        //endregion

        //region GetsDeviceOnClick
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
        //endregion

        //region OpensBTActivity
        mBlueButton = (ImageButton) findViewById(R.id.AddBlueDeviceButton);
        mBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBluetoothActivity();
            }
        });
        //endregion

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

        //region On Off Button
        OnOffButton = (ImageButton) findViewById(R.id.On_Off);
        OnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                byte[] temp;
                if (onoff == 1)
                {
                    temp = new byte[]{(byte) 255, (byte) 255, (byte) 255};
                    SelectedColor = 0;
                    String test = Integer.toHexString(SelectedColor);
                    bytes = temp ;
                    onoff = 0;
                }
                else
                {
                    temp = new byte[]{(byte) 0, (byte) 0, (byte) 0};
                    SelectedColor = Color.WHITE;
                    String test ="1"; //Integer.toHexString(SelectedColor);
                    bytes = test.getBytes();
                }
                SendBTMessage(bytes);


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

        //region List Activity Button
        mListActivitybtn = (ImageButton) findViewById(R.id.ListActivitybtn);
        mListActivitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenListActivity();
            }
        });
        //endregion


    }
    //endregion

    //region PassColorFromColor wheel
    @Override
    public void PassColor(String _Hex) {
        stringSelctedColor = _Hex;
        byte[] temp = _Hex.getBytes();
        bytes = temp;
        SendBTMessage(bytes);
    }
    //endregion

    //region BTSendRunnable Class
    class BTSendThread implements Runnable
    {
        byte[] _message;

        //region Constructor
        BTSendThread(byte[] message){
            _message = message;

        }
        //endregion

        //region Run
        @Override
        public void run() {
            sendbtmethod(_message);
        }
        //endregion
    }
    //endregion

    //region OpenColorPicker
    public void openColorPicker()
    {

            Color_Dialog Colorwheezl = new Color_Dialog();
            Colorwheezl.show(getSupportFragmentManager(),"ColorWheel");

    }
    //endregion

    //region Send Mehtods
    public void SendBTMessage(byte[] _message)
    {
        BTSendThread btrunable = new BTSendThread(_message);
        new Thread(btrunable).start();

    }
    private void sendbtmethod(byte[] _message)
    {
        if(mSelectedDevice != null) {

            BluetoothAdapter aAdapt = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice arduino = aAdapt.getRemoteDevice(mSelectedDevice.getAddress());
            BluetoothSocket mSocket = null;


            //region ConnectionMethod
            do {
                try {
                    mSocket = arduino.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                    mSocket.connect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } while (!mSocket.isConnected());
            //endregion


            //region OutputMessageMethod
            try {

                OutputStream OutPut = mSocket.getOutputStream();


                OutPut.write(_message);


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //endregion


            //region CloseConnection
            try {
                mSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //endregion


        }
    }
    //endregion

    //region ReciveMethod
    public void ReciveBTMessage(int _messageContainer)
    {

        BluetoothAdapter aAdapt = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice arduino = aAdapt.getRemoteDevice(mSelectedDevice.getAddress());
        BluetoothSocket mSocket = null;



        //region ConnectMethod
        do {
            try {
                mSocket = arduino.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                mSocket.connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }while (!mSocket.isConnected());
        //endregion


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
    //endregion

    //region OpasityBar On\Off
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
    //endregion

    //region Open BTActivity
    public void OpenBluetoothActivity()
    {
        Intent mopenBlue = new Intent(MainActivity.this, BlueToothActivity.class);
        startActivity(mopenBlue);

    }
    //endregion

    //region Open List Activity
    public void OpenListActivity()
    {
        Intent mOpenListact = new Intent(MainActivity.this, Group_Page.class);
        startActivity(mOpenListact);

    }
    //endregion

    //region RGBMethod
    public void RGBconvertMethod(int _Color)
    {
        RGBval[0] = (_Color & 0xFF0000) >> 16;
        RGBval[1] = (_Color & 0xFF00) >> 8;
        RGBval[2] = (_Color & 0xFF);


    }
    //endregion

    //region Preset Button color get/ send Method
    @Override
    public void onClick(View v) {

        PaintDrawable ColorDrawable = (PaintDrawable) v.getBackground();
        SelectedColor = ColorDrawable.getPaint().getColor();
        RGBconvertMethod(SelectedColor);
        String tests = Integer.toHexString(SelectedColor);

        byte[] temp = tests.getBytes();
        bytes = temp;
        SendBTMessage(bytes);


    }
    //endregion

    public void mOpenBluetoothActivity(View view)
    {
        Intent mopenMain = new Intent(MainActivity.this, BlueToothActivity.class);
        startActivity(mopenMain);

    }
    public  void  mOpenPresetActivity(View view)
    {
        Intent mOpenPresets = new Intent(MainActivity.this, PresetActivity.class);
        startActivity(mOpenPresets);
    }

}
