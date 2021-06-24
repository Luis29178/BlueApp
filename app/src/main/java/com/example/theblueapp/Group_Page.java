package com.example.theblueapp;

import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Page extends AppCompatActivity {

     private  static  final  int Gallery_Request_Code = 123;
    private ArrayList<BluetoothDevice> List1 = new ArrayList<>();
    private ArrayList<BluetoothDevice> List2 = new ArrayList<>();
    private ArrayList<BluetoothDevice> List3 = new ArrayList<>();
    private ArrayList<BluetoothDevice> List4 = new ArrayList<>();
    private ArrayList<BluetoothDevice> List5 = new ArrayList<>();
    private ArrayList<BluetoothDevice> List6 = new ArrayList<>();

    int Image = 0;

    ImageButton Button1;
    ImageButton Button2;
    ImageButton Button3;
    ImageButton Button4;
    ImageButton Button5;
    ImageButton Button6;

    Button AddList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page);
        

/*
        Button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                  builder.setTitle("");
                  builder.setMessage("Delete List 1?");
                  builder.setCancelable(true);
                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          List1 = new ArrayList<>();
                          Button1.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                          Button1.setVisibility(View.GONE);

                      }
                  });
                  builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  return false;

            }
        });
        Button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                builder.setTitle("");
                builder.setMessage("Delete List 2?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List2 = new ArrayList<>();
                        Button2.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                        Button2.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;

            }
        });
        Button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                builder.setTitle("");
                builder.setMessage("Delete List?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List3 = new ArrayList<>();
                        Button3.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                        Button3.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;

            }
        });
        Button4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                builder.setTitle("");
                builder.setMessage("Delete List?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List4 = new ArrayList<>();
                        Button4.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                        Button4.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;

            }
        });
        Button5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                builder.setTitle("");
                builder.setMessage("Delete List?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List5 = new ArrayList<>();
                        Button5.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                        Button5.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;

            }
        });
        Button6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Group_Page.this);

                builder.setTitle("");
                builder.setMessage("Delete List?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List6 = new ArrayList<>();
                        Button6.setBackground(Drawable.createFromPath("@drawable/ambilwarna_alphacheckered"));
                        Button6.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;

            }
        });

*/  //Long click detect use short time click to detect not long tap sice on click method is interventing with this

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });
        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });




        AddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick Image"), Gallery_Request_Code);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Request_Code && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();

            switch (Image) {
                case 1:
                    Button1.setImageURI(imageData);
                    break;
                case 2:
                    Button2.setImageURI(imageData);
                    break;
                case 3:
                    Button3.setImageURI(imageData);
                    break;
                case 4:
                    Button4.setImageURI(imageData);
                    break;
                case 5:
                    Button5.setImageURI(imageData);
                    break;
                case 6:
                    Button6.setImageURI(imageData);
                    break;

            }

        }
    }
}
