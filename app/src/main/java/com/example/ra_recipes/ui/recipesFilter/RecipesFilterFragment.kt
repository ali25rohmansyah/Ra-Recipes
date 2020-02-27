package com.example.ra_recipes.ui.recipesFilter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ra_recipes.R

class RecipesFilterFragment : Fragment() {

    companion object {
        fun newInstance() = RecipesFilterFragment()
    }

    private lateinit var viewModel: RecipesFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipesFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
