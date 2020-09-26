package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ALL")
public class Register extends AppCompatActivity {
    Button btn_generate;
    EditText password_generated;
    private long backPressedTime;
    private Toast backToast;
    HttpParse httpParse = new HttpParse();
    EditText reg_mail, reg_name;
    HashMap<String,String> hashMap = new HashMap<>();

    String F_Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;
    String HttpURL = "http://softcodes.tech/noticeboard/register.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        password_generated = findViewById(R.id.password);
        btn_generate = findViewById(R.id.btn_generate);

        reg_name = findViewById(R.id.reg_user_name);
        reg_mail = findViewById(R.id.reg_email);

        getSupportActionBar().hide();
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = 6;

                password_generated.setText(GetPassword(length));

            }
        });
    }


    public void register(View view) {

        if (ConnectivityReceiver.isConnected()) {
                  EmailHolder = reg_mail.getText().toString();
                  F_Name_Holder = reg_name.getText().toString();
                  PasswordHolder = password_generated.getText().toString();
                if (TextUtils.isEmpty(EmailHolder)) {
                    reg_mail.setError("Enter an Email");
                    reg_mail.requestFocus();
                    return;

                }
                // now checking mail validated
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EmailHolder).matches()) {
                    reg_mail.setError("Please Enter a Valid Email!");
                    reg_mail.requestFocus();
                }
                if (TextUtils.isEmpty(F_Name_Holder)) {
                    reg_name.setError("Name is Required");
                    reg_name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(PasswordHolder)) {
                    password_generated.setError("Generate or Enter a Password!");
                    password_generated.requestFocus();
                    return;
                }
                //new Register.Insert().execute();
            UserRegisterFunction(F_Name_Holder, EmailHolder, PasswordHolder);

        }

        else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection ");
            builder.setMessage("Do you want to go to settings");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent j = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(j);
                    //System.exit(0);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }
    public String GetPassword(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < length; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public void back_to_login(View view) {

        Intent back_to_login=new Intent(getApplicationContext(),Login.class);
        startActivity(back_to_login);
        finish();
    }

    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    /* testing something ...*/

    public void UserRegisterFunction(final String F_Name,  final String email, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Register.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("user_name",params[0]);

                hashMap.put("user_email",params[1]);

                hashMap.put("user_password",params[2]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,email,password);
    }

}
