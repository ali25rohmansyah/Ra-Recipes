package com.example.ra_recipes.view.ui.detailRecipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ra_recipes.database.FavoriteDAO
import com.example.ra_recipes.database.FavoriteDB
import com.example.ra_recipes.database.FavoriteRepository
import com.example.ra_recipes.network.DetailRecipesData
import com.example.ra_recipes.network.NutritionsData
import com.example.ra_recipes.network.SpoonacularApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailRecipesViewModel(id:String): ViewModel() {

    //Add repository
//    private val repository: FavoriteRepository
//    private val favoriteDao: FavoriteDAO = FavoriteDB.getInstance(application).FavoriteDAO()

//    private var _favoriteItems: LiveData<List<>>
    private val _item = MutableLiveData<DetailRecipesData>()
    private val _nutrition = MutableLiveData<NutritionsData>()

    val nutritions: LiveData<NutritionsData>
        get() = _nutrition
    val item : LiveData<DetailRecipesData>
        get() = _item

    private val vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.Main)

    init {
//        repository = FavoriteRepository(favoriteDao)

        Log.d("debug", "id = $id")
        crScope.launch {
            try {
                val result = SpoonacularApi.retrofitService.showInstructions(id)
                _item.postValue(result)
                val result2 = SpoonacularApi.retrofitService.showNutritions(id)
                _nutrition.postValue(result2)
                Log.d("debug", _item.toString())
            }catch (t: Throwable){
                Log.d("debug", "error = ${t.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}