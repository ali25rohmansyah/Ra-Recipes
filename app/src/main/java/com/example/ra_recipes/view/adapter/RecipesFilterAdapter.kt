package com.example.ra_recipes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ra_recipes.databinding.ItemRecipesBinding
import com.example.ra_recipes.network.RecipesData

class RecipesFilterAdapter(private val showDetail: (String) -> Unit): ListAdapter<RecipesData, RecipesFilterAdapter.RecipesViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder{
        return RecipesViewHolder(ItemRecipesBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int){
        val item = getItem(position)
        holder.bind(item)
    }

    inner class RecipesViewHolder(private var binding: ItemRecipesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(recipesData: RecipesData){
            binding.cardRecipe.setOnClickListener{
                showDetail(binding.txtId.text.toString())
            }
            binding.item = recipesData
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<RecipesData>(){
        override fun areItemsTheSame(oldItem: RecipesData, newItem: RecipesData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RecipesData, newItem: RecipesData): Boolean {
            return  oldItem.id  == newItem.id
        }
    }

}