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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class BlueToothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "BlueToothActivity";

    private BluetoothAdapter bBlueAdapter;

    //region Footer
    private Button fHome;
    private Button faddDevice;
    private  Button fPresets;
    //endregion

    private ArrayList<BluetoothDevice> bDeviceList;
    private Device_Adapter bDevAdapter;
    ListView  NewDevices;

    //region Creates Broadcast Receiver for Bluetooth
    private final BroadcastReceiver bBLueReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //checks to see if a device is found
            Log.d(TAG,"bBlueReciver1: Action Found chek");
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                Log.d(TAG,"bBlueReciver1: Action Found");
                //Gets the Bluetooth device
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //adds the device to device list
                bDeviceList.add(device);
                Log.d(TAG,"Receiver1 Device: "+device.getName()+" Adress: "+device.getAddress());
                bDevAdapter = new Device_Adapter(context,R.layout.bluetooth_view,bDeviceList );
                NewDevices.setAdapter(bDevAdapter);
            }

        }



    };
    //endregion

    //region Broadcast Receiver for Pairing
    private final  BroadcastReceiver pBrodcastReciverBond = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String bConnAction = intent.getAction();

            if(bConnAction.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice pDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // this will give 3 outcomes 1: when device is already bonded(paired) 2: device will be bonding(pairing) 3: device will brake bond(Disconnect/unPair)
                // will log state of Bonding
                if (pDevice.getBondState() == BluetoothDevice.BOND_BONDED)
                {
                    Log.d(TAG,"pBrodcastReciverBond: BONDED");

                }
                if (pDevice.getBondState() == BluetoothDevice.BOND_BONDING)
                {
                    Log.d(TAG,"pBrodcastReciverBond: BONDING");
                }
                if (pDevice.getBondState() == BluetoothDevice.BOND_NONE)
                {
                    Log.d(TAG,"pBrodcastReciverBond: Braking BOND");
                }

            }
        }
    };
    //endregion


    //region Destructor
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bBLueReceiver1);
        unregisterReceiver(pBrodcastReciverBond);

    }
    //endregion

    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



        setContentView(R.layout.bluetooth_discov_layout);
        NewDevices = (ListView) findViewById(R.id.BlueNewDevices);
        bDeviceList = new ArrayList<>();
        bBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter pBondFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(pBrodcastReciverBond, pBondFilter);
        NewDevices.setOnItemClickListener(BlueToothActivity.this);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bBlueDiscoveryButton(View view) {
        Log.d(TAG, "Discovery: Looking for devices.");

        if (bBlueAdapter.isDiscovering())
        {
            bBlueAdapter.cancelDiscovery();
            Log.d(TAG,"BlueDiscovery: Canceling");


            checkBTPerm();
            bBlueAdapter.startDiscovery();
            IntentFilter discoverI = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bBLueReceiver1,discoverI);

        }
        else if(!bBlueAdapter.isDiscovering())
        {
            checkBTPerm();
            bBlueAdapter.startDiscovery();
            IntentFilter discoverI2 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bBLueReceiver1,discoverI2);
        }

    }

    /**
     * This is a requerd check for all devices runing API23+
     * Android must chek bluetoothe permisions programmatically.
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPerm() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            int check = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            check += this.checkSelfPermission("Manifest.permission.ACCESS_CORSE_LOCATION");
            if(check != 0)
            {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0001);
            }else
            {
                Log.d(TAG,"check: Canceled SDK vs. < LOLLIPOP");
            }

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //you always cancel discover to insure it dosent eat up memory
        bBlueAdapter.cancelDiscovery();
        Log.d(TAG,"Device Selected");

        String dName = bDeviceList.get(position).getName();
        String dAddress = bDeviceList.get(position).getAddress();
        //this check is to make suer its obove API is 19 or > as its required for creatBond to work
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT);
        {
            Log.d(TAG,dName+" "+dAddress+"pairing");
            bDeviceList.get(position).createBond();
        }
        Log.d(TAG,dName+" Paired");
        Intent bIntent = new Intent(BlueToothActivity.this,MainActivity.class);
        BluetoothDevice bExtra = bDeviceList.get(position);
        bIntent.putExtra("ADDED_DEVICE", bExtra );
        startActivity(bIntent);


    }



    //region Open Activitys
    public void bOpenBluetoothActivity(View view)
    {
        // stays empty since in main activity

    }
    public  void  bOpenPresetActivity(View view)
    {
        Intent mOpenPresets = new Intent(BlueToothActivity.this, PresetActivity.class);
        startActivity(mOpenPresets);
    }
    public void bOpenHomeActivity(View view)
    {
        Intent mopenMain = new Intent(BlueToothActivity.this, MainActivity.class);
        startActivity(mopenMain);


    }
    //endregion
}



