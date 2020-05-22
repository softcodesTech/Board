package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.board.adapters.Reply_Adapter;
import com.example.board.util.Reply_News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reply_Comment extends AppCompatActivity {

    private static final String FULL_STORY = "http://softcodes.tech/newsportal/fully_story_api.php";
    List<Reply_News> productList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_center);
        recyclerView = findViewById(R.id.reply_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        full_story();
    }

    private void full_story() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FULL_STORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            final ProgressBar progressBar = findViewById(R.id.reply_progressBar);
                            progressBar.setVisibility(View.VISIBLE);
                            setProgressBarIndeterminateVisibility(true);

                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);


                                productList.add(new Reply_News(
                                        product.getInt("id"),
                                        product.getString("PostTitle"),
                                        product.getString("PostImage"),
                                        product.getString("PostingDate"),
                                        product.getString("PostDetails"),
                                        product.getString("CategoryName")
            ));
                                Reply_Adapter adapter = new Reply_Adapter(Reply_Comment.this, productList);
                                recyclerView.setAdapter(adapter);

                                progressBar.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Reply_Comment.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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

    public void writing_a_comment(View view) {
        Intent comment=new Intent(getApplicationContext(),Comment_Now.class);
        startActivity(comment);
    }
}
