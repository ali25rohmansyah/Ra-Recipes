package com.example.ra_recipes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ra_recipes.databinding.ItemNutritionsBinding
import com.example.ra_recipes.network.DetailNutritions

class NutritionsAdapter(): ListAdapter<DetailNutritions, NutritionsAdapter.NutritionsViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NutritionsAdapter.NutritionsViewHolder {
        return NutritionsViewHolder(ItemNutritionsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NutritionsAdapter.NutritionsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class NutritionsViewHolder(private val binding: ItemNutritionsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(detailNutritions: DetailNutritions){
            binding.item = detailNutritions
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<DetailNutritions>(){
        override fun areItemsTheSame(
            oldItem: DetailNutritions,
            newItem: DetailNutritions
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DetailNutritions,
            newItem: DetailNutritions
        ): Boolean {
           return oldItem.title == newItem.title
        }

    }

}