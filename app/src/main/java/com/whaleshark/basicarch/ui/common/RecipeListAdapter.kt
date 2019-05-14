package com.whaleshark.basicarch.ui.common

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup

import com.whaleshark.basicarch.R
import com.whaleshark.basicarch.databinding.RecipeItemBinding
import com.whaleshark.basicarch.model.Recipe
import com.whaleshark.basicarch.util.Objects

class RecipeListAdapter(private val repoClickCallback: RecipeClickCallback?) : DataBoundListAdapter<Recipe, RecipeItemBinding>() {

    override fun createBinding(parent: ViewGroup): RecipeItemBinding {
        val binding: RecipeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recipe_item, parent, false)

        binding.root.setOnClickListener { v ->
            val repo = binding.getRecipe()
            if (repo != null && repoClickCallback != null) {
                repoClickCallback.onClick(repo)
            }
        }
        return binding
    }

    override fun bind(binding: RecipeItemBinding, item: Recipe) {
        binding.recipe = item
    }

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return Objects.equals(oldItem.title, newItem.title) && Objects.equals(oldItem.title, newItem.title)
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return Objects.equals(oldItem.title, newItem.title) && oldItem.ingredients == newItem.ingredients
    }

    interface RecipeClickCallback {
        fun onClick(recipe: Recipe)
    }
}
