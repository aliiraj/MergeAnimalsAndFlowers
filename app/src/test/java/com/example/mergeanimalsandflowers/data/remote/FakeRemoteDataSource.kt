package com.example.mergeanimalsandflowers.data.remote

import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

class FakeRemoteDataSource(
    private var animalItem: MainAnimalResponseDto,
    private var flowerItem: MainFlowerResponseDto
): RemoteDataSource{

    override suspend fun getAnimals(): Flow<MainAnimalResponseDto> {
        return flow { animalItem }
    }

    override suspend fun getFlowers(): Flow<MainFlowerResponseDto> {
        return flow { flowerItem }
    }
}