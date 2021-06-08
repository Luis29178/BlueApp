package com.example.theblueapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Color_Dialog extends AppCompatDialogFragment {
    private ImageView Color_wheel;
    private SeekBar Red;
    private TextView RedVal;
    private ProgressBar _Red;
    private SeekBar Green;
    private TextView GreenVal;
    private ProgressBar _Green;
    private SeekBar Blue;
    private TextView BlueVal;
    private ProgressBar _Blue;



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

            }
        });
        //endregion

        //region Variable Initialization
        Color_wheel = view.findViewById(R.id.ColorWheelHUE);
        Red = view.findViewById(R.id.RedBar);
        RedVal = view.findViewById(R.id.RedVal);
        _Red = view.findViewById(R.id.RedPro);
        Green = view.findViewById(R.id.GreenBar);
        GreenVal = view.findViewById(R.id.GreenVal);
        _Green = view.findViewById(R.id.GreenPro);
        Red = view.findViewById(R.id.BlueBar);
        RedVal = view.findViewById(R.id.BlueVal);
        _Red = view.findViewById(R.id.BluePro);
        //endregion




        return builder.create();

    }
}
