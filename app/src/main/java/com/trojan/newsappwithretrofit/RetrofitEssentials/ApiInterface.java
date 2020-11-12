package com.trojan.newsappwithretrofit.RetrofitEssentials;

import com.trojan.newsappwithretrofit.Models.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<Headlines> seacrhHeadlines(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
