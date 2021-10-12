package com.example.mergeanimalsandflowers.data.local

import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    suspend fun insertMergedAnimalFlower(items: List<AnimalAndFlowerMergedEntity>)

    fun getAllMergedAnimalFlower(): Flow<List<AnimalAndFlowerMergedEntity>>

    fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedEntity>>

    suspend fun saveLastUpdateMergedItems(timestamp: Long)

    suspend fun getLastUpdateMergedItems(): Long?

}