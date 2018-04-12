package com.whaleshark.basicarch.ui.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.whaleshark.basicarch.R;
import com.whaleshark.basicarch.databinding.RecipeItemBinding;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.util.Objects;

public class RecipeListAdapter extends DataBoundListAdapter<Recipe, RecipeItemBinding> {
    private final RecipeClickCallback repoClickCallback;

    public RecipeListAdapter(RecipeClickCallback repoClickCallback) {
        this.repoClickCallback = repoClickCallback;
    }

    @Override
    protected RecipeItemBinding createBinding(ViewGroup parent) {
        RecipeItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.recipe_item, parent, false);

        binding.getRoot().setOnClickListener(v -> {
            Recipe repo = binding.getRecipe();
            if (repo != null && repoClickCallback != null) {
                repoClickCallback.onClick(repo);
            }
        });
        return binding;
    }

    @Override
    protected void bind(RecipeItemBinding binding, Recipe item) {
        binding.setRecipe(item);
    }

    @Override
    protected boolean areItemsTheSame(Recipe oldItem, Recipe newItem) {
        return Objects.equals(oldItem.title, newItem.title) &&
                Objects.equals(oldItem.title, newItem.title);
    }

    @Override
    protected boolean areContentsTheSame(Recipe oldItem, Recipe newItem) {
        return Objects.equals(oldItem.title, newItem.title) && oldItem.ingredients.equals(newItem.ingredients);
    }

    public interface RecipeClickCallback {
        void onClick(Recipe recipe);
    }
}
