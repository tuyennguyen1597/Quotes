package com.example.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowQuotes extends AppCompatActivity {
    private static final String TAG = "ShowQuotes";
    public static String catName;
    TextView title, content;
    Button showQuote;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_quotes);

        //declare the View
        title = findViewById(R.id.titleCat);
        content = findViewById(R.id.quotesContent);
        showQuote = findViewById(R.id.showBtn);

        //Make an intent to receive the categories value which is passed from MainActivity
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            catName = (String) extras.get("categories");
            title.setText(catName);
        }

        Retrofit retrofit = CustomRetrofit.getRetrofit();
        QuotesRetrofit quotesRetrofit = retrofit.create(QuotesRetrofit.class);

        View.OnClickListener clickedQuote = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Quotes> quotesCall = quotesRetrofit.getQuoteData(catName);
                quotesCall.enqueue(new Callback<Quotes>() {
                    @Override
                    public void onResponse(Call<Quotes> call, Response<Quotes> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + catName);
                            content.setText(response.body().getValue());
                        }
                    }

                    @Override
                    public void onFailure(Call<Quotes> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
            }
        };

        showQuote.setOnClickListener(clickedQuote);
    }
}
