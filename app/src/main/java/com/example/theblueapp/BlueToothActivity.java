package com.example.theblueapp;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class BlueToothActivity extends AppCompatActivity   {
    private static final String TAG = "BlueToothActivity";

    private BluetoothAdapter bBlueAdapter;

    private ArrayList<BluetoothDevice> bDeviceList = new ArrayList<>();
    private Device_Adapter bDevAdapter;
    ListView  NewDevices;

    //region Creates Broadcast Receiver for Bluetooth
    private final BroadcastReceiver bBLueReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //checks to see if a device is found
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                //Gets the Bluetooth device
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //adds the device to device list
                bDeviceList.add(device);
                Log.d(TAG,"Receiver1 Device: "+device.getName()+" Adress: "+device.getAddress());
                bDevAdapter = new Device_Adapter(context,R.layout.bluetooth_discov_layout,bDeviceList );
                NewDevices.setAdapter(bDevAdapter);
            }

        }



    };
    //endregion


    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_discov_layout);
        NewDevices = (ListView) findViewById(R.id.BlueNewDevices);
        bDeviceList =new ArrayList<>();
        bBlueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void BlueDiscoveryButton(View view) {
        Log.d(TAG, "Discovery: Looking for devices.");

        if (bBlueAdapter.isDiscovering())
        {
            bBlueAdapter.cancelDiscovery();
            Log.d(TAG,"BlueDiscovery: Canceling");


            bBlueAdapter.startDiscovery();
            IntentFilter discoverI = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bBLueReceiver1,discoverI);

        }
        else if(!bBlueAdapter.isDiscovering())
        {
            bBlueAdapter.startDiscovery();
            IntentFilter discoverI2 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bBLueReceiver1,discoverI2);
        }

    }






}
