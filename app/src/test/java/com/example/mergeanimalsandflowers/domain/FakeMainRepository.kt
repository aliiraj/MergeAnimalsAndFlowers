package com.example.mergeanimalsandflowers.domain

import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.domain.repositories.MainRepository
import com.example.mergeanimalsandflowers.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException

class FakeMainRepository(
    val items: MutableList<AnimalAndFlowerMergedModel>
) : MainRepository {


    var isError = false
    var isEmptyData = false

    override suspend fun getAllMergedAnimalFlower(forceToRefresh: Boolean): Flow<Resource<List<AnimalAndFlowerMergedModel>>> =
        flow {
            if (forceToRefresh || isEmptyData) {
                emit(Resource.Loading(items.toList()))
                if (isError) {
                    emit(Resource.Error(NullPointerException(), items.toList()))
                } else {
                    emit(Resource.Success(items.toList()))
                }
            } else {
                emit(Resource.Success(items.toList()))
            }
        }

    override fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedModel>> =
        flow {
            val filteredItems =
                items.filter { it.animalName.contains(query) || it.flowerName.contains(query) }
            emit(filteredItems)
        }
}