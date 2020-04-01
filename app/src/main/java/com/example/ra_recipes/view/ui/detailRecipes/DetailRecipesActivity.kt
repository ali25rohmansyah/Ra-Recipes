package com.example.ra_recipes.view.ui.detailRecipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ethanhua.skeleton.Skeleton
import com.example.ra_recipes.R
import com.example.ra_recipes.databinding.ActivityDetailRecipesBinding
import com.example.ra_recipes.view.adapter.IngredientsAdapter
import com.example.ra_recipes.view.adapter.NutritionsAdapter
import com.example.ra_recipes.view.adapter.RecipesFilterAdapter

class DetailRecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipesBinding
    private lateinit var viewModel: DetailRecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_detail_recipes)
        binding.lifecycleOwner = this

        //get id
        val id = intent.getStringExtra("id")
        val vmfactory = DetailRecipesViewModelFactory(id!!)
        this.viewModel = ViewModelProviders.of(this, vmfactory).get(DetailRecipesViewModel::class.java)
        binding.viewModel = viewModel

        //Add recyclerView ingredients
        val viewAdapter  = IngredientsAdapter()
        binding.ingredientRecyclerView.adapter = viewAdapter
        viewModel.item.observe(this, Observer { viewAdapter.submitList(it.ingredients) })

        //Add recyclerView nutritions
        val viewAdapterNutritions = NutritionsAdapter()
        binding.nutritionalRecyclerView.adapter = viewAdapterNutritions
        viewModel.nutritions.observe(this, Observer {
            viewAdapterNutritions.submitList(it.good)
            viewAdapterNutritions.submitList(it.bad)
        })


        //Add loading skeleton
//        val skeletonScreen = Skeleton.bind(binding.recyclerView)
//            .adapter(viewAdapter)
//            .load(R.layout.item_recipes)
//            .show()

//        recipesFilterViewModel.response.observe(this, Observer {
//            if(it != "1") skeletonScreen.hide()
//        })
    }
}
