package com.example.mergeanimalsandflowers.data.repositories

import com.example.mergeanimalsandflowers.data.apis.AnimalApis
import com.example.mergeanimalsandflowers.data.models.MainAnimalResponseModel
import com.example.mergeanimalsandflowers.utils.Resource
import com.example.mergeanimalsandflowers.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AnimalRepository @Inject constructor(
    private val animalApis: AnimalApis
) {

    suspend fun getAnimals() = flow {
        emit(safeApiCall { animalApis.getAnimals() })
    }

}