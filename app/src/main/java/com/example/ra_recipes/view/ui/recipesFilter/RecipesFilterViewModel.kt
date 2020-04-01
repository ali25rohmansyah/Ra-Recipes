package com.example.ra_recipes.view.ui.recipesFilter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ra_recipes.network.RecipesData
import com.example.ra_recipes.network.SpoonacularApi
import com.example.ra_recipes.network.SpoonacularService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipesFilterViewModel : ViewModel() {
    private var _response = MutableLiveData<String>()
    private var _label = MutableLiveData<String>()
    private var _items = MutableLiveData<List<RecipesData>>()

    val response: MutableLiveData<String>
        get() = _response
    val label: MutableLiveData<String>
        get() = _label
    val items: LiveData<List<RecipesData>>
        get() = _items

    private val vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.Main)

    init {
        initData()
    }

    private fun initData() {
        crScope.launch {
            _response.value = "1"
            try {
                val result = SpoonacularApi.retrofitService.showList()

                if (result.isNotEmpty()) {
                    _items.value = result
                    _response.postValue("2")
                }

            }catch (t: Throwable){
                _response.value = t.message
                Log.i("error", _response.value)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}
