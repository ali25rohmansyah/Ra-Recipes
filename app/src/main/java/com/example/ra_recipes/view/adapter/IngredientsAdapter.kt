package com.example.ra_recipes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ra_recipes.databinding.ItemIngredientsBinding
import com.example.ra_recipes.network.Ingredients

class IngredientsAdapter: ListAdapter<Ingredients, IngredientsAdapter.IngredientsViewHolder>(DiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class IngredientsViewHolder(private val binding: ItemIngredientsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ingredients: Ingredients){
            binding.item = ingredients
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Ingredients>(){
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return  oldItem.name  == newItem.name
        }
    }
}