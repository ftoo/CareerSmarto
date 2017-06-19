package com.example.shiro.careersmart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import static com.example.shiro.careersmart.R.layout.test_dialogbox;

import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class SplashScreen extends AppCompatActivity {

    ImageView test, professional, personality;
    GridView androidGridView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        test = (ImageView) findViewById(R.id.img_test);
        personality = (ImageView) findViewById(R.id.img_personality);
        professional = (ImageView) findViewById(R.id.img_professional);


        test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(SplashScreen.this);
                final View foView = layoutInflaterAndroid.inflate(test_dialogbox, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(SplashScreen.this);
                alertDialogBuilderUserInput.setView(foView);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                Intent intent = new Intent(SplashScreen.this, Realistic.class);
                                startActivity(intent);


                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();


            }
        });
        personality.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashScreen.this, Knowyou.class);
                startActivity(intent);

            }
        });
        professional.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashScreen.this, Careers.class);
                startActivity(intent);

            }
        });


    }
}
