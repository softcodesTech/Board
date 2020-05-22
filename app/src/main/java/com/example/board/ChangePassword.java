package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ChangePassword extends AppCompatActivity {

    EditText tvOldPsw,tvNewPsw,tvConfPsw;
    Button bChange;
    String forgotemail_holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tvOldPsw = findViewById(R.id.otp);
        tvNewPsw = findViewById(R.id.new_psw);
        tvConfPsw = findViewById(R.id.conf_psw);
        bChange = findViewById(R.id.change);


        init();
        tvOldPsw.setEnabled(false);
        tvOldPsw.setFocusable(false);
        tvOldPsw.setFocusableInTouchMode(true);

    }


    void init(){

        Intent intent = getIntent();
        forgotemail_holder = intent.getStringExtra(ForgotPassword.UserEmail);
        tvOldPsw.setText(forgotemail_holder);


    }

    public void change_password(View view) {

        String passwo = tvNewPsw.getText().toString();
        String conpasswo = tvConfPsw.getText().toString();
        if(TextUtils.isEmpty(passwo)){
            tvNewPsw.setError("Enter A new Password!");
            tvNewPsw.requestFocus();
            return;
        }
        if(!passwo.matches(conpasswo)){
            tvNewPsw.requestFocus();
            tvNewPsw.setError("Passwords must match");
            return;
        }
        if(TextUtils.isEmpty(conpasswo)){
            tvNewPsw.requestFocus();
            tvNewPsw.setError("Enter a new Password");
        }

        final String confirmPassword = tvConfPsw.getText().toString();
        if (!TextUtils.isEmpty(passwo) && !TextUtils.isEmpty(confirmPassword)) {
            if (!passwo.equals(confirmPassword)) {
                //are equal
                tvNewPsw.requestFocus();

                tvNewPsw.setError("Password must match");
                //return;
            }

    }
        new update_my_password().execute();
}

    @SuppressLint("StaticFieldLeak")
    public class update_my_password extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChangePassword.this);
            dialog.setMessage("saving, please wait");
            dialog.setTitle("Connecting... ");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            @SuppressLint("WrongThread")
            final String New_password = tvNewPsw.getText().toString();
            @SuppressLint("WrongThread") final String email = tvOldPsw.getText().toString();



            try {

                List<NameValuePair> insert = new ArrayList<NameValuePair>();
                insert.add(new BasicNameValuePair("user_password", New_password));
                insert.add(new BasicNameValuePair("user_email", email));


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://softcodes.tech/noticeboard/update_password.php"); // link to connect to database
                httpPost.setEntity(new UrlEncodedFormEntity(insert));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                return true;


            } catch (IOException e) {
                e.printStackTrace();

            }


            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();

            AlertDialog.Builder ac = new AlertDialog.Builder(ChangePassword.this);
            ac.setTitle("Password");
            ac.setMessage("Updated successful!");
            ac.setCancelable(true);

            ac.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            tvNewPsw.setText("");
                            tvConfPsw.setText("");



                        }
                    });

            AlertDialog alert = ac.create();
            alert.show();
        }

    }
}
