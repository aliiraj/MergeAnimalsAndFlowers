package com.example.mergeanimalsandflowers.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mergeanimalsandflowers.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter(private val sliderItems: List<String>):
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.itemSliderBinding.apply {
            imageUrl = sliderItems[position]
        }
    }

    override fun getCount() = sliderItems.size

    class SliderViewHolder(val itemSliderBinding: ItemSliderBinding) :
        SliderViewAdapter.ViewHolder(itemSliderBinding.root)
}