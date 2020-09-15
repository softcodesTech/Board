package com.example.board;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;


@SuppressWarnings("ALL")
public class FeedbackSuggestion extends AppCompatActivity {
    EditText et_feedback;
    TextView counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_suggestion);
        et_feedback = findViewById(R.id.feedback_lect);
        checkInternetConenction();
        counter = (TextView)findViewById(R.id.upper_counter);

        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = et_feedback.length();
                String convert = String.valueOf(length);
                counter.setText(convert+"/280");
                if (convert.equals(280/280)){
                    et_feedback.setError("Maximum words Reached!");
                    et_feedback.setEnabled(false);

                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }


    public void savefeedback(View view) {
        final String feedback_message = et_feedback.getText().toString();
        //validations
        if (TextUtils.isEmpty(feedback_message)) {
            et_feedback.setError("Please Enter Feedback Message");
            et_feedback.requestFocus();
            return;
        }
        new Insert().execute();
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
    public class Insert extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FeedbackSuggestion.this);
            dialog.setMessage("saving FeedBack, please wait");
            dialog.setTitle("Connecting... ");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            @SuppressLint("WrongThread") final String feedback_message = et_feedback.getText().toString();


            try {

                List<NameValuePair> insert = new ArrayList<NameValuePair>();
                insert.add(new BasicNameValuePair("feedback_name", feedback_message));


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://newsportal.next256.com/feedback.php"); // link to connect to database
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

            AlertDialog.Builder ac = new AlertDialog.Builder(FeedbackSuggestion.this);
            ac.setTitle("FeedBack");
            ac.setMessage("Your Feedback is  sent!");
            ac.setCancelable(true);

            ac.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            et_feedback.setText("");


                        }
                    });

            AlertDialog alert = ac.create();
            alert.show();
        }

    }
}
