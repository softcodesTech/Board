package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.agrawalsuneet.dotsloader.loaders.ZeeLoader;
import com.example.board.util.PrefManager;
import com.example.board.util.User;

import static com.example.board.Login.MY_PREFS_NAME;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
        getSupportActionBar().hide();
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Logout.this);

        // Set the message show for the Alert time
        builder.setMessage("Are You sure you want to logout?");

        // Set Alert Title
        builder.setTitle("Logout !");


        builder.setCancelable(false);



        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                finish();
                                Intent logout = new Intent(Logout.this, Home.class);
                                startActivity(logout);
                                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor preferencesEditor = prefs.edit();
                                preferencesEditor.clear();
                                preferencesEditor.apply();
                            }
                        });

        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                dialog.cancel();
                                Intent logout = new Intent(Logout.this, Bsu_Settings.class);
                                startActivity(logout);
                                finish();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
        ZeeLoader n = findViewById(R.id.imgshow);
        ZeeLoader zeeLoader = new ZeeLoader(
                this,
                60,
                4,
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.red));

        zeeLoader.setAnimDuration(200);

        n.addView(zeeLoader);
    }
}
