package com.whaleshark.basicarch.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author stipton
 */

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }
}
