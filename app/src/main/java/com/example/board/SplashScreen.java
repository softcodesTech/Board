package com.example.board;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;

public class SplashScreen extends AppCompatActivity {
    LazyLoader lazyLoader;
    Handler handler;
    Runnable runnable;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        // Hiding the Action bar programatically
        getSupportActionBar().hide();

        lazyLoader = findViewById(R.id.myLoaderProgress);
        if (ConnectivityReceiver.isConnected()) {

            if (savedInstanceState == null) {

                LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected));
                loader.setAnimDuration(500);
                loader.setFirstDelayDuration(100);
                loader.setSecondDelayDuration(200);
                loader.setInterpolator(new LinearInterpolator());
                lazyLoader.addView(loader);
                // Zeeloader implementation
                img = findViewById(R.id.img);
                img.animate().alpha(4000).setDuration(0);

            }

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent dsp = new Intent(SplashScreen.this,Home.class);
                    startActivity(dsp);
                    finish();
                }
            },4000);


        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection ");
            builder.setMessage("Do you want to go to settings");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent j = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(j);
                    System.exit(0);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    System.exit(0);
                }
            });
            builder.show();
        }



    }
}
