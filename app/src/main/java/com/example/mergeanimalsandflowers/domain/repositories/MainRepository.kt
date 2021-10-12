package com.example.mergeanimalsandflowers.domain.repositories

import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MainRepository {

    suspend fun getAllMergedAnimalFlower(forceToRefresh: Boolean): Flow<Resource<List<AnimalAndFlowerMergedModel>>>

    fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedModel>>

}