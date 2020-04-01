package com.example.ra_recipes.view.ui.detailRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailRecipesViewModelFactory(private val id: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  DetailRecipesViewModel(id) as T
    }

}