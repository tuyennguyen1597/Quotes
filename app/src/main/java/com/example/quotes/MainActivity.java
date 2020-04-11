package com.example.quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static String categoryName;
    RecyclerView recyclerView;
    static List<String> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare the View foe RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //Make a Retrofit from BaseURL
        //Call it from the custom class CustomRetrofit
        Retrofit retrofit = CustomRetrofit.getRetrofit();
        QuotesRetrofit quotesRetrofit = retrofit.create(QuotesRetrofit.class);

        //Make a quote listener which is able to response to click request
        //to item in the RecyclerView
        QuotesAdapter.QuotesListener listener = new QuotesAdapter.QuotesListener() {
            @Override
            public void onClick(View v, int position) {
                clickResponse(position);
            }
        };

        //Get a Call retrofit from the Quotes interface
        //in order to get the link to the categories list
        Call<List<String>> catCall = quotesRetrofit.getCatData();

        //Get the categories data from the ChuckNorris API using enqueue
        catCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Successful");
                    categories = response.body();
                    QuotesAdapter adapter = new QuotesAdapter(categories, MainActivity.this, listener);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +t.getLocalizedMessage());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void clickResponse(int position) {
        Intent intent = new Intent(MainActivity.this, ShowQuotes.class);
        intent.putExtra( "categories", categories.get(position));
        categoryName = categories.get(position);
        MainActivity.this.startActivity(intent);
    }
}
