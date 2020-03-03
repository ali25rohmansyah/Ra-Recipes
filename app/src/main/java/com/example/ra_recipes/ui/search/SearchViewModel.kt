package com.example.ra_recipes.ui.search

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _image = MutableLiveData<ArrayList<Bitmap>>().apply {
    }
    val image: LiveData<ArrayList<Bitmap>>
    get() = _image

    init {
        _image.value = arrayListOf<Bitmap>()
    }
}