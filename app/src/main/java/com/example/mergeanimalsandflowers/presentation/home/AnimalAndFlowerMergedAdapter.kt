package com.example.mergeanimalsandflowers.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mergeanimalsandflowers.databinding.ItemAnimalFlowerBinding
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel


class AnimalAndFlowerMergedAdapter(val selectItem: (item: AnimalAndFlowerMergedModel) -> Unit) :
    ListAdapter<AnimalAndFlowerMergedModel, AnimalAndFlowerMergedAdapter.FoodViewHolder>(FoodDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemAnimalFlowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.apply {
            item = getItem(position)
            btnShowDetails.setOnClickListener { selectItem(getItem(position)) }
        }
    }

    class FoodViewHolder(val binding: ItemAnimalFlowerBinding) : RecyclerView.ViewHolder(binding.root)

    object FoodDiffCallback : DiffUtil.ItemCallback<AnimalAndFlowerMergedModel>() {
        override fun areItemsTheSame(
            oldItem: AnimalAndFlowerMergedModel,
            newItem: AnimalAndFlowerMergedModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AnimalAndFlowerMergedModel,
            newItem: AnimalAndFlowerMergedModel
        ): Boolean {
            return oldItem.animalName == newItem.animalName
        }
    }
}

