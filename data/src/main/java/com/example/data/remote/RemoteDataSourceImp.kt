package com.example.data.remote

import com.example.data.remote.apis.AnimalApis
import com.example.mergeanimalsandflowers.data.remote.apis.FlowerApis
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val animalApis: AnimalApis,
    private val flowerApis: FlowerApis
): RemoteDataSource {

    override suspend fun getAnimals() = flow {
        emit(animalApis.getAnimals())
    }

    override suspend fun getFlowers() = flow {
        emit(flowerApis.getFlowers())
    }

}