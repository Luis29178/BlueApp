package com.example.theblueapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Color_Dialog extends AppCompatDialogFragment {
    private ImageView Color_wheel;
    private SeekBar cRed;
    private TextView cRedVal;
    private ProgressBar _cRed;
    private SeekBar cGreen;
    private TextView cGreenVal;
    private ProgressBar _cGreen;
    private SeekBar cBlue;
    private TextView cBlueVal;
    private ProgressBar _cBlue;

    Bitmap bitmap;
    String BTHEXcolor;

    private Color_DialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.color_piker, null);

        //region DialogButtons
        builder.setView((view)).setTitle("Color Wheel")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String color = BTHEXcolor;
                listener.PassColor(color);


            }
        });
        //endregion

        //region Variable Initialization
        Color_wheel = view.findViewById(R.id.ColorWheelHUE);


        cRed = view.findViewById(R.id.RedBar);
        cRedVal = view.findViewById(R.id.RedVal);
        _cRed = view.findViewById(R.id.RedPro);

        cGreen = view.findViewById(R.id.GreenBar);
        cGreenVal = view.findViewById(R.id.GreenVal);
        _cGreen = view.findViewById(R.id.GreenPro);

        cBlue = view.findViewById(R.id.BlueBar);
        cBlueVal = view.findViewById(R.id.BlueVal);
        _cBlue = view.findViewById(R.id.BluePro);
        //endregion

        Color_wheel.setDrawingCacheEnabled(true);
        Color_wheel.buildDrawingCache(true);
        Color_wheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = Color_wheel.getDrawingCache();
                    int color = 0;
                    if ((int) event.getX() >= 0 && (int) event.getX() < bitmap.getWidth() && (int)event.getY() >= 0 && (int)event.getY() < bitmap.getHeight()){

                        //int x = (int) event.getX();
                        //int y = (int) event.getY();
                        //int r = bitmap.getWidth()/2;


                        //int radialcheck = ((x)^2) + ((y)^2) ;
                        //if(radialcheck < (r^2)){

                            color = bitmap.getPixel((int)event.getX(),(int)event.getY());

                        //}

                    }



                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);

                    cRedVal.setText(String.valueOf(r));
                    cGreenVal.setText(String.valueOf(g));
                    cBlueVal.setText(String.valueOf(b));
                    cRed.setProgress(r);
                    cGreen.setProgress(g);
                    cBlue.setProgress(b);
                    /*_cRed.setProgress(r);
                    _cGreen.setProgress(g);
                    _cBlue.setProgress(b);*/

                    BTHEXcolor = '<'+ String.valueOf(r) +'+'+ String.valueOf(g) +'-'+ String.valueOf(b)+'>';


                }
                return true;
            }
        });





        return builder.create();

    }


    @Override
     public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            listener = (Color_DialogListener) context;
        }catch (ClassCastException x)
        {
            throw new ClassCastException(context.toString()+"must implement Example Listener");

        }
    }



    public  interface  Color_DialogListener{
        void PassColor(String _Hex);
    }
}
