package com.example.data.mapper

import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity


class MainRepositoryMapper {

    fun mapEntitiesToModels(type: List<AnimalAndFlowerMergedEntity>?): List<AnimalAndFlowerMergedModel> {
        return type?.map {
            AnimalAndFlowerMergedModel(
                animalName = it.animalName,
                flowerName = it.flowerName,
                animalImage = it.animalImage,
                flowerImage = it.flowerImage,
                commonChars = it.commonChars
            )
        } ?: emptyList()
    }

}