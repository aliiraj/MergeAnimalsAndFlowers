package com.example.domain.repositories

import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MainRepository {

    suspend fun getAllMergedAnimalFlower(forceToRefresh: Boolean): Flow<Resource<List<AnimalAndFlowerMergedModel>>>

    fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedModel>>

}