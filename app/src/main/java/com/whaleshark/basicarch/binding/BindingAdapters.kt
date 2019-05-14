package com.whaleshark.basicarch.binding

import android.databinding.BindingAdapter
import android.widget.ImageView

import com.bumptech.glide.Glide

/**
 * @author stipton
 */

object BindingAdapters {

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String) {
        Glide.with(imageView).load(url).into(imageView)
    }
}
