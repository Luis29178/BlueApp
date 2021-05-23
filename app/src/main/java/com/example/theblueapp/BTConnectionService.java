package com.example.theblueapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.content.Context;

import java.io.IOException;
import java.util.UUID;

public class BTConnectionService {
    private static final String TAG ="BTCS";

    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");//Universal HC_05 modual UUID

    private final BluetoothAdapter sBlueAdapter;
    Context sContext;


    public BTConnectionService(Context context) {
        sContext = context;
        sBlueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private class AcceptThread extends Thread
    {
        private final BluetoothServerSocket sServerSocket;

        public AcceptThread()
        {
            BluetoothServerSocket temp = null;

            try {
                temp = sBlueAdapter.listenUsingInsecureRfcommWithServiceRecord("TheBlueApp",MY_UUID_INSECURE);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            sServerSocket = temp;

        }


    }
}
