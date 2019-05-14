package com.whaleshark.basicarch;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import com.whaleshark.basicarch.ui.common.RecipeListAdapter;
import com.whaleshark.basicarch.util.TextChangedListener;
import com.whaleshark.basicarch.viewmodel.SearchViewModel;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.repo_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final RecipeListAdapter adapter = new RecipeListAdapter(recipe -> Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);

        SearchViewModel recipeSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        recipeSearchViewModel.getResults().observe(this, recipes -> adapter.replace(recipes));

        EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 ) {
                    adapter.replace(null);
                    return;
                }

                recipeSearchViewModel.setQuery(s.toString());
            }
        });
    }
}
