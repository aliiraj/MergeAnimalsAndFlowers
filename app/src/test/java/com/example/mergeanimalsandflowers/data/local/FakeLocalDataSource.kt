package com.example.mergeanimalsandflowers.data.local

import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource: LocalDataSource {

    private val givenItems = ArrayList<AnimalAndFlowerMergedEntity>()
    private var lastUpdate: Long? = null

    override suspend fun insertMergedAnimalFlower(items: List<AnimalAndFlowerMergedEntity>) {
        givenItems.addAll(items)
    }

    override fun getAllMergedAnimalFlower(): Flow<List<AnimalAndFlowerMergedEntity>> {
        return flow { emit(givenItems) }
    }

    override fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedEntity>> {
        val filteredItems = givenItems.filter { it.animalName.contains(query) || it.flowerName.contains(query) }
        return flow { emit(filteredItems) }
    }

    override suspend fun saveLastUpdateMergedItems(timestamp: Long) {
        lastUpdate = timestamp
    }

    override suspend fun getLastUpdateMergedItems(): Long? {
        return lastUpdate
    }
}