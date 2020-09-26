package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class ForgotPassword extends AppCompatActivity {
    EditText email_entered;
    String finalResult;
    String HttpURL = "http://newsportal.softcodes.tech/check_email.php";
    ProgressDialog progressDialog;
    public static String UserEmail = "";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        email_entered = findViewById(R.id.email_forgot);
        getSupportActionBar().hide();
        checkInternetConenction();
    }

    public void request_forgot_password(View view) {
        final String email = email_entered.getText().toString();

        if (TextUtils.isEmpty(email)) {
            email_entered.setError("Email Address is Required");
            email_entered.requestFocus();
            return;
        }
        // checking email validity and refer to what is stored in the db
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_entered.setError("Enter a valid email");
            email_entered.requestFocus();
            return;
        }
        check_email(email);
    }

    public boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            new AlertDialog.Builder(this)
                    .setTitle("Connect Network?")
                    .setMessage("Please Connect your phone to Wifi or enable data")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            // DCALogin.super.onBackPressed();
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                        }
                    }).create().show();
            return false;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public void check_email(final String email) {

        @SuppressLint("StaticFieldLeak")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ForgotPassword.this, "Checking Email in Database", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();



                if (httpResponseMsg.equalsIgnoreCase("Email verified")) {
                    Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
                    intent.putExtra(UserEmail, email);
                    startActivity(intent);
                    finish();

                } else {



                    Toast.makeText(ForgotPassword.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("user_email", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }


        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email);

    }
}
