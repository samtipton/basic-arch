package com.whaleshark.basicarch.ui.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.whaleshark.basicarch.R;
import com.whaleshark.basicarch.databinding.RecipeItemBinding;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.util.Objects;

/**
 * @author stipton
 */

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
        return Objects.INSTANCE.equals(oldItem.getTitle(), newItem.getTitle()) &&
                Objects.INSTANCE.equals(oldItem.getTitle(), newItem.getTitle());
    }

    @Override
    protected boolean areContentsTheSame(Recipe oldItem, Recipe newItem) {
        return Objects.INSTANCE.equals(oldItem.getTitle(), newItem.getTitle()) && oldItem.getIngredients().equals(newItem.getIngredients());
    }

    public interface RecipeClickCallback {
        void onClick(Recipe recipe);
    }
}
