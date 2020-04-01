package com.example.ra_recipes.view.ui.recipesFilter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.ra_recipes.R
import com.example.ra_recipes.databinding.FragmentRecipesFilterBinding
import com.example.ra_recipes.view.adapter.RecipesFilterAdapter
import com.example.ra_recipes.view.ui.detailRecipes.DetailRecipesActivity

class RecipesFilterFragment : Fragment() {

    private lateinit var binding: FragmentRecipesFilterBinding
    private lateinit var recipesFilterViewModel: RecipesFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var args =
            RecipesFilterFragmentArgs.fromBundle(
                arguments!!
            )
        recipesFilterViewModel = ViewModelProviders.of(this).get(RecipesFilterViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes_filter, container, false);

        //dataBinding and LiveData
        binding.setLifecycleOwner(this)
        binding.recipesFilterViewModel = recipesFilterViewModel

        recipesFilterViewModel.label.value = args.label

        //Add recyclerView
        val viewAdapter  = RecipesFilterAdapter {item->showDetail(item)}
        binding.recyclerView.adapter = viewAdapter

        recipesFilterViewModel.items.observe(this, Observer { viewAdapter.submitList(it) })

        //Add loading skeleton
        val skeletonScreen = Skeleton.bind(binding.recyclerView)
            .adapter(viewAdapter)
            .load(R.layout.item_recipes)
            .show()

        recipesFilterViewModel.response.observe(this, Observer {
            if(it != "1") skeletonScreen.hide()
        })

        return binding.root
    }

    fun showDetail(id: String){
        val intent = Intent(activity, DetailRecipesActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}
