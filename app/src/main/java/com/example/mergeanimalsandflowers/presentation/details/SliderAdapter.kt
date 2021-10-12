package com.example.mergeanimalsandflowers.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mergeanimalsandflowers.databinding.ItemSliderBinding


class SliderAdapter(private val sliderItems: List<String>):
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.itemSliderBinding.apply {
            imageUrl = sliderItems[position]
        }
    }

    override fun getItemCount() = sliderItems.size

    class SliderViewHolder(val itemSliderBinding: ItemSliderBinding) :
        RecyclerView.ViewHolder(itemSliderBinding.root)
}