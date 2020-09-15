package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.board.adapters.Business_Adapter;
import com.example.board.util.Business_News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Guild extends AppCompatActivity {

    private static final String URL_PRODUCTS = "http://newsportal.next256.com/guild_api.php";
    List<Business_News> productList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business);
        recyclerView = findViewById(R.id.business_recylcerView);
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
                            final ProgressBar progressBar = findViewById(R.id.progressBar1);

                            //Displaying Progressbar
                            progressBar.setVisibility(View.VISIBLE);
                            setProgressBarIndeterminateVisibility(true);
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Business_News(
                                        product.getString("id"),
                                        product.getString("PostTitle"),
                                        product.getString("PostImage"),
                                        product.getString("PostingDate"),
                                        product.getString("PostDetails"),
                                        product.getString("comment")



                                ));
                                Business_Adapter adapter = new Business_Adapter(Guild.this, productList);
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
}
