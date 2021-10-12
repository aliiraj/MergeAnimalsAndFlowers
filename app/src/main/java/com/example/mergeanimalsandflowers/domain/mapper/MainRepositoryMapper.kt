package com.example.mergeanimalsandflowers.domain.mapper

import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel


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