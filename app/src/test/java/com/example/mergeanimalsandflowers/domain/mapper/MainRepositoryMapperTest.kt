package com.example.mergeanimalsandflowers.domain.mapper

import com.example.data.mapper.MainRepositoryMapper
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MainRepositoryMapperTest {

    @Test
    fun mapEntitiesToModels() {
        val entity1 = AnimalAndFlowerMergedEntity("animal1", "flower1", "", "", emptyList())
        val entity2 = AnimalAndFlowerMergedEntity("animal2", "flower2", "", "", emptyList())
        val entities = mutableListOf(entity1, entity2)

        val model1 = AnimalAndFlowerMergedModel("animal1", "flower1", "", "", emptyList())
        val model2 = AnimalAndFlowerMergedModel("animal2", "flower2", "", "", emptyList())
        val models = mutableListOf(model1, model2)

        val mainRepositoryMapper = MainRepositoryMapper()
        val convertedModels = mainRepositoryMapper.mapEntitiesToModels(entities)

        assertThat(convertedModels).isEqualTo(models)
    }
}