package com.whaleshark.basicarch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.whaleshark.basicarch.model.Recipe

import com.whaleshark.basicarch.ui.common.RecipeListAdapter
import com.whaleshark.basicarch.util.TextChangedListener
import com.whaleshark.basicarch.viewmodel.SearchViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.repo_recycler)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter = RecipeListAdapter(object : RecipeListAdapter.RecipeClickCallback{
            override fun onClick(recipe: Recipe) {
                Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = adapter

        val recipeSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        recipeSearchViewModel.results.observe(this, Observer { recipes -> adapter.replace(recipes) })

        val editText = findViewById<EditText>(R.id.edit_text)
        editText.addTextChangedListener(object : TextChangedListener() {
            override fun afterTextChanged(s: Editable) {
                if (s.length == 0) {
                    adapter.replace(null)
                    return
                }

                recipeSearchViewModel.setQuery(s.toString())
            }
        })
    }
}
