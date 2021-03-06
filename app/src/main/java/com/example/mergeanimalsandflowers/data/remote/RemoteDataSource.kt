package com.example.mergeanimalsandflowers.data.remote

import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import com.example.mergeanimalsandflowers.utils.Resource
import kotlinx.coroutines.flow.Flow


interface RemoteDataSource {

    suspend fun getAnimals(): Flow<MainAnimalResponseDto>

    suspend fun getFlowers(): Flow<MainFlowerResponseDto>
}