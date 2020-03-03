package com.example.ra_recipes.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ra_recipes.R
import com.example.ra_recipes.databinding.ItemSelectedImageBinding
import com.example.ra_recipes.ui.search.SearchViewModel

class SelectedImageAdapter(private val viewModel: SearchViewModel) :
    RecyclerView.Adapter<SelectedImageAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSelectedImageBinding) : RecyclerView.ViewHolder(binding.root){
        val img_selected = binding.imgSelected
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedImageAdapter.MyViewHolder {
        // create a new view
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSelectedImageBinding.inflate(inflater)
        return MyViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.img_selected.setImageBitmap(viewModel.image.value!![position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = viewModel.image.value!!.size
}