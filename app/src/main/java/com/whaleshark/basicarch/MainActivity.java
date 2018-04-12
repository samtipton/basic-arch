package com.whaleshark.basicarch;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import com.whaleshark.basicarch.ui.common.RecipeListAdapter;
import com.whaleshark.basicarch.util.TextChangeListener;
import com.whaleshark.basicarch.viewmodel.SearchViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.repo_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        EditText editText = findViewById(R.id.edit_text);
        final RecipeListAdapter adapter = new RecipeListAdapter(recipe -> Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);

        SearchViewModel recipeSearchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        editText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                recipeSearchViewModel.setQuery(s.toString());
            }
        });

        recipeSearchViewModel.getResults().observe(this, recipes -> adapter.replace(recipes));
    }
}
