package com.example.ra_recipes.view.ui.search

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
    private val _status = MutableLiveData<String>()
    private val _label = MutableLiveData<ArrayList<String>>()

    val label: LiveData<ArrayList<String>>
        get() = _label
    val status: LiveData<String>
        get() = _status

    private val vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.IO)

    init {
        _label.value = arrayListOf()
        _status.value = "processing an image"
    }

    fun predictImage(file: File){

        crScope.launch {
            try {
                Log.d("debug", "mulai")
                val client = ClarifaiBuilder("ce702309aab54ca8a279c2693210701c").buildSync()
                var predictionResults: List<ClarifaiOutput<Concept>>
                predictionResults = client.defaultModels.foodModel().predict()
                    .withMinValue(0.9)
                    .withInputs(ClarifaiInput.forImage(file))
                    .executeSync()
                    .get()
                Log.d("debug", "mulai 2")
                for (result in predictionResults) {
                    for (datum in result.data()) {
                        _label.value?.add(datum.name().toString())
                        Log.d("debug", datum.name().toString())
                    }
                }
                _status.postValue("done")
                Log.d("debug", _status.value.toString())
            }catch (t: Throwable){
                _status.postValue(t.message)
                Log.d("debug", t.message!!)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}