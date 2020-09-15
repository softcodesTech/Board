package com.example.board;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.board.adapters.Comment_Adapter;
import com.example.board.util.Comment_News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comment_Now extends AppCompatActivity {
    int count =0;
    private static final String URL_PRODUCTS = "http://newsportal.next256.com/read_comments.php";
    List<Comment_News> productList;
    RecyclerView recyclerView;
    String post_comment;
    HttpParse httpParse = new HttpParse();
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult ;
    String HttpURL = "http://newsportal.next256.com/save_comment.php";
    ProgressDialog progressDialog;
    EditText com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment__now);
        com = findViewById(R.id.commented);
        recyclerView = findViewById(R.id.comment_recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        loadbusiness_news();
    }


    private void loadbusiness_news() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            final ProgressBar progressBar = findViewById(R.id.progressBar1);
                            progressBar.setVisibility(View.VISIBLE);
                            setProgressBarIndeterminateVisibility(true);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Comment_News(
                                        product.getString("id"),
                                        product.getString("name"),
                                        product.getString("comment"),
                                        product.getString("postingDate")
                                        //product.getString("PostingDate")
                                       // product.getString("PostingDate")



                                ));
                                Comment_Adapter adapter = new Comment_Adapter(Comment_Now.this, productList);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void liked(View view) {

        TextView like_this = (TextView) findViewById(R.id.most_likes);
        count++;
        like_this.setText(String.valueOf(count));

    }

    public void post_a_comment(View view) {

        if (ConnectivityReceiver.isConnected()) {
            post_comment = com.getText().toString();

            if (TextUtils.isEmpty(post_comment)) {
                com.setError("Enter a comment");
                com.requestFocus();
                return;

            }

            //new Register.Insert().execute();
            UserRegisterFunction(post_comment);


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

    public void UserRegisterFunction(final String comment){

        @SuppressLint("StaticFieldLeak")
        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Comment_Now.this,"Posting Comment",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Comment_Now.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                com.setText("");
                Intent comment=new Intent(getApplicationContext(),Comment_Now.class);
                startActivity(comment);
                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("comment",params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(comment);
    }
}
