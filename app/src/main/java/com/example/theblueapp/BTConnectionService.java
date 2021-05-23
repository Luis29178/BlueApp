package com.example.theblueapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BTConnectionService {
    private static final String TAG ="BTCS";

    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");//Universal HC_05 modual UUID

    private AcceptThread sInsicureAcceptThread;
    private ConnectThread sConnectThread;
    private BluetoothDevice sDevice;
    private UUID deviceUUID;
    ProgressDialog sProgressDialog;
    private ConnectedThread sConnectedThread;

    private final BluetoothAdapter sBlueAdapter;
    Context sContext;


    public BTConnectionService(Context context)
    {
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
        public void run()
        {
            BluetoothSocket sSocket = null;

            try {
                sSocket = sServerSocket.accept();
                Log.d(TAG,"Connection Made");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (sSocket != null)
            {
                connected(sSocket,sDevice);
            }
        }
        public void cancel()
        {
            try {
                sServerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }




    }
    private class ConnectThread extends Thread
    {
        private BluetoothSocket scSocket;
        public ConnectThread(BluetoothDevice device, UUID _UUID)
        {
            sDevice = device;
            deviceUUID = _UUID;
        }
        public void run()
        {
            BluetoothSocket temp = null;
            try {
                temp = sDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            scSocket=temp;

            sBlueAdapter.cancelDiscovery();

            try {

                scSocket.connect();

            } catch (IOException ioException) {

                try {
                    scSocket.close();

                } catch (IOException e) {

                    //e.printStackTrace();
                }

                //ioException.printStackTrace();
            }
            connected(scSocket,sDevice);
        }
        public void cancel()
        {
            try {

                scSocket.close();

            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }

        }



    }
    public synchronized void start()
    {
        if (sConnectThread != null)
        {
            sConnectThread.cancel();
            sConnectThread = null;
        }
        if (sInsicureAcceptThread == null)
        {
            sInsicureAcceptThread = new AcceptThread();
            sInsicureAcceptThread.start();

        }


    }
    public void startClient(BluetoothDevice _device, UUID _UUID)
    {
        sProgressDialog = sProgressDialog.show(sContext,"Connecting","Please Wait", true);
        sConnectThread = new ConnectThread(_device,_UUID);
        sConnectThread.start();
    }
    private class ConnectedThread extends Thread
    {
        private final BluetoothSocket sccSocket;
        private final InputStream _InStream;
        private final OutputStream _OutStream;


        private ConnectedThread(BluetoothSocket sccSocket)
        {
            this.sccSocket = sccSocket;
            InputStream tmpInStream = null;
            OutputStream tmpOutStream = null;
            sProgressDialog.dismiss();
            try {
                tmpInStream = sccSocket.getInputStream();
                tmpOutStream = sccSocket.getOutputStream();
            } catch (IOException ioException) {
                Log.d(TAG,"Failed In/outStream");
            }
            _InStream = tmpInStream;
            _OutStream =tmpOutStream;

        }
        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;
            while(true)
            {
                try {
                    bytes = _InStream.read(buffer);

                } catch (IOException ioException) {
                Log.d(TAG,"Failed INStream read");
                break;
                }

            }

        }
        public void write(byte[] bytes)
        {
            try {
                _OutStream.write(bytes);
            } catch (IOException ioException) {
                Log.d(TAG,"OutStream Failed");
            }
        }


        public void cancel()
        {
            try {
                sccSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }





    }

}
