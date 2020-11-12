package com.trojan.newsappwithretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.trojan.newsappwithretrofit.Models.Articles;
import com.trojan.newsappwithretrofit.Models.Headlines;
import com.trojan.newsappwithretrofit.RetrofitEssentials.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    final String apiKey = "dd8204d7493747e993c53347cad838f7";
    List<Articles> articles = new ArrayList<>();

    EditText edtsearch;
    ImageView btnSearch;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtsearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retriveJson();
            }
        });

        retriveJson();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retriveJson();
            }
        });
    }

    private void retriveJson() {

        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;
        if (!edtsearch.getText().toString().isEmpty()) {
            String query = edtsearch.getText().toString().trim();
            call = ApiClient.getInstance().getApi().seacrhHeadlines(query, apiKey);
        } else {
            String country = getCountry();
            call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        }

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    swipeRefreshLayout.setRefreshing(false);

                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("myTag", t.getLocalizedMessage());
                System.out.println(t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "Failed : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    @Override
    public void onBackPressed() {
        if (!edtsearch.getText().toString().isEmpty()) {
            edtsearch.getText().clear();
            retriveJson();
        } else {
            super.onBackPressed();
        }
    }
}