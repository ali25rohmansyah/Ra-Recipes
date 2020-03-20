package com.example.ra_recipes.ui.search

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class SearchViewModel : ViewModel() {
    private val _image = MutableLiveData<ArrayList<String>>()
    private val _img = MutableLiveData<File>()
    private val _status = MutableLiveData<String>()
    private val _label = MutableLiveData<ArrayList<String>>()

    val label: LiveData<ArrayList<String>>
        get() = _label
    val status: LiveData<String>
        get() = _status
    val img: LiveData<File>
        get() = _img
    val image: LiveData<ArrayList<String>>
        get() = _image

    private val job = Job()
    private val crScope = CoroutineScope(job + Dispatchers.IO)
    init {
        initData()
    }

    private fun initData() {
        _status.value = "processing an image"
        _image.value = arrayListOf<String>()
    }


}