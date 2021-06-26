package com.example.theblueapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PresetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presets_layout);

    }
    //region Open Activitys
    public void OpenBluetoothActivity(View view)
    {
        Intent mopenBlue = new Intent(PresetActivity.this, BlueToothActivity.class);
        startActivity(mopenBlue);

    }
    public  void  OpenPresetActivity(View view)
    {
        // stays empty since in curr activity
    }
    public void OpenHomeActivity(View view)
    {
        Intent mOpenPresets = new Intent(PresetActivity.this, MainActivity.class);
        startActivity(mOpenPresets);


    }
    //endregion
}
