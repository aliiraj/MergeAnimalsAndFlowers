package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AnimalAndFlowerMergedModel(
    var animalName: String,
    var flowerName: String,
    val animalImage: String,
    val flowerImage: String,
    val commonChars: List<Char>,
): Parcelable
