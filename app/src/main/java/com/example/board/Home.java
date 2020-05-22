package com.example.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.example.board.adapters.Business_Adapter;
import com.example.board.util.Business_News;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

@SuppressWarnings("ALL")
public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener{

    BottomNavigationView bottomNavigation;
    CarouselView carouselView;
    int[] sampleImages =
            {R.drawable.ar_office, R.drawable.bsu, R.drawable.campus, R.drawable.bsu, R.drawable.ar_office};

    private long backPressedTime;
    private Toast backToast;

    //this is the JSON Data URL
    private static final String URL_PRODUCTS = "http://softcodes.tech/newsportal/home_api.php";
    List<Business_News> productList;
    RecyclerView recyclerView;

    HttpParse httpParse = new HttpParse();
    EditText reg_mail, reg_name;
    HashMap<String,String> hashMap = new HashMap<>();
    String F_Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;
    String HttpURL = "http://softcodes.tech/newsportal/save_clicked_id.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.business_recylcerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadbusiness_news();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.home_land:
                            Intent ii = new Intent(getApplicationContext(), Home.class);
                            startActivity(ii);
                            return true;
                        case R.id.view_category:
                            Intent i = new Intent(getApplicationContext(),Board_Categories.class);
                            startActivity(i);
                            return true;
                        case R.id.video:
                            Intent ji = new Intent(getApplicationContext(), Notice_Videos.class);
                            startActivity(ji);
                            return true;
                        case R.id.settings:
                            Intent settings_profile = new Intent(getApplicationContext(), Bsu_Settings.class);
                            startActivity(settings_profile);
                            return true;
                        case  R.id.favorite:
                           // un comment this ...
                            Intent favorites = new Intent(getApplicationContext(), Bsu_Favorites.class);
                            startActivity(favorites);

                            return true;
                    }
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, Home_Searchable_Data.class)));
        searchView.setIconifiedByDefault(false);
        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


    private void loadbusiness_news() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

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
                                Business_Adapter adapter = new Business_Adapter(Home.this, productList);
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

    @Override
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
//TECNO TECNO K7 Android 7.0, API 24
    //open_full_story

    public void open_full_story(View view) {

        TextView id_save =(TextView)findViewById(R.id.clicked_post);

       final String na = id_save.getText().toString().trim();

        Saveclicked_post(na);


    }
    public void Saveclicked_post(final String id_saved){

        class Saveclicked_post extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Home.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();

                //Toast.makeText(Home.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                Intent view_classes = new Intent(Home.this, Reply_Comment.class);
                startActivity(view_classes);

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("clicked_id",params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        Saveclicked_post saveclicked_post = new Saveclicked_post();

        saveclicked_post.execute(id_saved);
    }

}
