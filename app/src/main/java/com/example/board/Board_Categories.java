package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

public class Board_Categories extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board__categories);
        handleIntent(getIntent());

        View card1 = findViewById(R.id.card1);
        View card2 = findViewById(R.id.finance_card);
        View card3 = findViewById(R.id.ar_card);
        View card4 = findViewById(R.id.library_card);
        View card5 = findViewById(R.id.guild_card);
        View card6 = findViewById(R.id.law_card);
        View card7 = findViewById(R.id.applied_card);
        View card8 = findViewById(R.id.business_card);
        View card9 = findViewById(R.id.card9);
        View card10 = findViewById(R.id.card10);


        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        card7.setOnClickListener(this);
        card8.setOnClickListener(this);
        card9.setOnClickListener(this);
        card10.setOnClickListener(this);
    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.card1:
                Intent intent = new Intent(Board_Categories.this, Chaplaincy.class);
                startActivity(intent);
                break;
            case R.id.finance_card:
                Intent i = new Intent(Board_Categories.this, Finance.class);
                startActivity(i);
                break;
            case R.id.ar_card:
                Intent ar = new Intent(Board_Categories.this, Ar_office.class);
                startActivity(ar);
                break;
            case R.id.library_card:
                Intent lib = new Intent(Board_Categories.this, Library.class);
                startActivity(lib);
                break;
            case R.id.guild_card:
                Intent guild = new Intent(Board_Categories.this, Guild.class);
                startActivity(guild);
                break;
            case R.id.law_card:
                Intent law = new Intent(Board_Categories.this, Law.class);
                startActivity(law);
                break;
            case R.id.applied_card:
                Intent appl = new Intent(Board_Categories.this, Applied_Sciences.class);
                startActivity(appl);
                break;
            case R.id.business_card:
                Intent bsucard = new Intent(Board_Categories.this, Business.class);
                startActivity(bsucard);
                break;
            case R.id.card9:
                Intent car = new Intent(Board_Categories.this, Education.class);
                startActivity(car);
                break;
            case R.id.card10:
                Intent ruha = new Intent(Board_Categories.this, Ruharo_campus.class);
                startActivity(ruha);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_category, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_category).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onNewIntent(Intent intent) {

        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


}
