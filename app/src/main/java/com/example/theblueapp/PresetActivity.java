package com.example.theblueapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class PresetActivity extends AppCompatActivity {
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presets_layout);

    }

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

    //region Send Mehtods
    private void SendBTMessage(byte[] _message)
    {
        PresetActivity.BTSendThread btrunable = new PresetActivity.BTSendThread(_message);
        new Thread(btrunable).start();

    }
    private void sendbtmethod(byte[] _message)
    {
        //BluetoothDevice mSelectedDevic= new ;
       if(getIntent().getExtras() != null) {
           //mSelectedDevice = getIntent().getExtras().getParcelable("currDevice");
       }


        // mSelectedDevice = new BluetoothDevice();

        if(true){//mSelectedDevice != null) {

            BluetoothAdapter aAdapt = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice arduino = aAdapt.getRemoteDevice("00:14:03:05:0E:EC");
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

    //region Open Activitys
    public void OpenBluetoothActivity(View view)
    {
        Intent mopenBlue = new Intent(PresetActivity.this, BlueToothActivity.class);
        BluetoothDevice temp = getIntent().getExtras().getParcelable("currDevice");
        mopenBlue.putExtra("currDevice", temp);
        startActivity(mopenBlue);

    }
    public  void  OpenPresetActivity(View view)
    {
        // stays empty since in curr activity
    }
    public void OpenHomeActivity(View view)
    {
        Intent mOpenPresets = new Intent(PresetActivity.this, MainActivity.class);
        BluetoothDevice temp = getIntent().getExtras().getParcelable("currDevice");
        mOpenPresets.putExtra("currDevice", temp);
        startActivity(mOpenPresets);


    }
    //endregion

    public void StaticMode(View view)
    {
        String  Static = "<1+1-1*S>";
        byte[] message =  Static.getBytes();
        SendBTMessage(message);

    }
    public void FladeMode(View view)
    {
        AlertDialog.Builder options = new AlertDialog.Builder(PresetActivity.this);
        View pview = getLayoutInflater().inflate(R.layout.checkbox_fade,null);

        Button okbtn = (Button)pview.findViewById(R.id.pOkbtn);
        Button cancelbtn = (Button)pview.findViewById(R.id.pCancelbtn);

        options.setView(pview);

        AlertDialog alertDialog = options.create();
        alertDialog.setCanceledOnTouchOutside(false);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });







        String  Static = "<255+1-255*F>";
        byte[] message =  Static.getBytes();
        SendBTMessage(message);

    }


    public void RainbowMode(View view)
    {
        String  Static = "<1+1-1*R>";
        byte[] message =  Static.getBytes();
        SendBTMessage(message);

    }










}
