package com.example.ra_recipes.view.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("showThumbnail")
fun showThumbnail(imgView: ImageView, url: String?){
    Glide.with(imgView.context)
        .load(url)
        .into(imgView)
}

@BindingAdapter("showIngredient")
fun showIngredient(imgView: ImageView, url: String?){
    Glide.with(imgView.context)
        .load("https://spoonacular.com/cdn/ingredients_100x100/$url")
        .into(imgView)
}