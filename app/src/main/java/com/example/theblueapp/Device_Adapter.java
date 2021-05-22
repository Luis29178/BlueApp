package com.example.theblueapp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Device_Adapter extends ArrayAdapter<BluetoothDevice> {
        // creates needed variables
        private LayoutInflater aLayoutInflater;
        private ArrayList<BluetoothDevice> aDevicesL;
        private int aViewID;
    //Constructor
    public Device_Adapter(@NonNull Context context, int watch_resource, ArrayList<BluetoothDevice> devices) {
        super(context, watch_resource,devices);
        this.aDevicesL = devices;
        aLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aViewID = watch_resource;

    }
    // this gets the Bluetooth layout and sets devices name and address
    public View getView(int position, View viewconv, ViewGroup parent)
    {
        viewconv = aLayoutInflater.inflate(aViewID,null);

        BluetoothDevice device = aDevicesL.get(position);
        //checks for a device
        if (device != null)
        {
            TextView DeviceN = (TextView) viewconv.findViewById(R.id.DeviceName);
            TextView DeviceA = (TextView) viewconv.findViewById(R.id.DeviceAdress);
            //checks if the device has a name for the diplay list
            if (DeviceN != null)
            {
                DeviceN.setText(device.getName());
            }
            //checks for an adress and then copys it to the displayed list
            if (DeviceA != null)
            {
                DeviceA.setText(device.getAddress());
            }
            aDevicesL.set(position,device);

        }
        //returns the layout
        return viewconv;
    }
}
