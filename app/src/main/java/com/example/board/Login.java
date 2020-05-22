package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    private static final String Email ="" ;
    private static final String Pasword ="" ;
    EditText username, password;

    String finalResult;
    String HttpURL = "http://softcodes.tech/noticeboard/user_login.php";
    ProgressDialog progressDialog;
    public static String UserEmail = "";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String name;
    public static String UserPassword = "";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.bsunoticeboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        username = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    }



    public void forgotpassword(View view) {
        Intent back_to_login=new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(back_to_login);
    }

    public void signin(View view) {
        if (ConnectivityReceiver.isConnected()) {

            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (TextUtils.isEmpty(user)) {
                username.setError("Email Address is required!");
                username.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                password.setError("Password is Required!");
                password.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                username.setError("Please Enter a Valid Email!");
                username.requestFocus();
            }
            // enabling registered users to login
           UserLoginFunction(user, pass);

        }

        else {

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

                }
            });
            builder.show();
        }
    }

    //if it passes all the validations
    @SuppressLint("StaticFieldLeak")
    public void UserLoginFunction(final String email, final String password) {

        @SuppressLint("StaticFieldLeak")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Login.this, "Checking...", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                if (httpResponseMsg.equalsIgnoreCase("Logged in Successfully")) {

                    Intent intent = new Intent(Login.this, Home.class);

                    intent.putExtra(UserEmail, email);
                    intent.putExtra(UserPassword,password);

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("userEmail", email);
                    editor.putString("userPassword", password);
                    editor.apply();
                    startActivity(intent);
                    finish();
//                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
//                    preferencesEditor.putString(Email, email);
//                    preferencesEditor.putString(Pasword, password);
//                    preferencesEditor.apply();

                } else {

                    Toast.makeText(Login.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("user_email", params[0]);

                hashMap.put("user_password", params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }


        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email, password);

    }

    public void register_new_user(View view) {
        Intent back_to_login=new Intent(getApplicationContext(),Register.class);
        startActivity(back_to_login);
        finish();
    }
}
