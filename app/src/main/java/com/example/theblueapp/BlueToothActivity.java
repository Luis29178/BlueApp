package com.example.theblueapp;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class BlueToothActivity extends AppCompatActivity   {
    private static final String TAG = "BlueToothActivity";

    private ArrayList<BluetoothDevice> bDeviceList = new ArrayList<>();
    private Device_Adapter bDevAdapter;
    ListView  NewDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_discov_layout);


        }


}
