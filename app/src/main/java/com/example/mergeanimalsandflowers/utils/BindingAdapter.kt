package com.example.mergeanimalsandflowers.utils

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.google.android.material.imageview.ShapeableImageView


@BindingAdapter("animalAndFlowerName")
fun AppCompatTextView.setText(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel){
    text = animalAndFlowerMergedModel.animalName.plus(" / ").plus(animalAndFlowerMergedModel.flowerName)
}

@BindingAdapter("commonChars")
fun AppCompatTextView.setTextCommonChars(chars: List<Char>){
    text = chars.toString().replace("[", "").replace("]", "").replace(",", " ، ")
}

@BindingAdapter("android:src")
fun loadImage(view: ShapeableImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}