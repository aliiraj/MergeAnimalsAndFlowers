package com.example.mergeanimalsandflowers.data.repositories

import com.example.mergeanimalsandflowers.data.apis.FlowerApis
import com.example.mergeanimalsandflowers.data.models.MainFlowerResponseModel
import com.example.mergeanimalsandflowers.utils.Resource
import com.example.mergeanimalsandflowers.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FlowerRepository @Inject constructor(
    private val flowerApis: FlowerApis
) {

    suspend fun getFlowers() = flow {
        emit(safeApiCall { flowerApis.getFlowers() })
    }
}