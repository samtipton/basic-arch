package com.whaleshark.basicarch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.whaleshark.basicarch.api.FoodPuppyService;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.model.RecipeSearchResult;
import com.whaleshark.basicarch.ui.common.RecipeListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Collections.EMPTY_LIST;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    final FoodPuppyService foodPuppyService = new Retrofit.Builder()
            .baseUrl("http://www.recipepuppy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodPuppyService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.repo_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final RecipeListAdapter adapter = new RecipeListAdapter(recipe -> Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show());

        recyclerView.setAdapter(adapter);

        EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 ) {
                    adapter.replace(null);
                    return;
                }

                foodPuppyService.listRepos(s.toString()).enqueue(new Callback<RecipeSearchResult>() {
                    @Override
                    public void onResponse(Call<RecipeSearchResult> call, Response<RecipeSearchResult> response) {
                        Log.d(TAG, "onResponse: " + response.isSuccessful());
                        if (response.isSuccessful()) {
                            adapter.replace(response.body().results);
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchResult> call, Throwable t) {

                    }
                });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}
