package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.agrawalsuneet.dotsloader.loaders.PullInLoader;
import com.example.board.util.PrefManager;

public class Welcome extends AppCompatActivity {
    PullInLoader lazyLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        TextView txt = findViewById(R.id.powered_by);
        txt.setSelected(true);

        init();
    }

    void init(){
        Button profile = findViewById(R.id.btn_profile);
        Button login = findViewById(R.id.btn_login);
        Button sign_up = findViewById(R.id.btn_sign_up);

    }

    public void skipthis(View view) {
        Intent skip = new Intent(Welcome.this,Register.class);
        startActivity(skip);
        finish();
    }


    public void skip_already_customer(View view) {
        Intent skip = new Intent(Welcome.this,Login.class);
        startActivity(skip);
        finish();
    }

    public void skip_to_register(View view) {
        Intent register = new Intent(Welcome.this,Home.class);
        startActivity(register);
        finish();
    }
}
