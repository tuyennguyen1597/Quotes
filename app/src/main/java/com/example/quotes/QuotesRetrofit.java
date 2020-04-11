package com.example.quotes;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuotesRetrofit {
    @GET("jokes/categories")
        Call<List<String>> getCatData();

    @GET("jokes/random")
        Call<Quotes> getQuoteData(@Query("category") String catName)                                                              ;
}
