package com.example.mergeanimalsandflowers.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mergedEntity", primaryKeys = ["animalName", "flowerName"])
data class AnimalAndFlowerMergedEntity(
    var animalName: String,
    var flowerName: String,
    val animalImage: String,
    val flowerImage: String,
    val commonChars: List<Char>
)
