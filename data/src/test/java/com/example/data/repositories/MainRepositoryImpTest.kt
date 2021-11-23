package com.example.data.repositories

import app.cash.turbine.test
import com.example.data.mapper.MainRepositoryMapper
import com.example.data.remote.RemoteDataSource
import com.example.data.utils.Resource
import com.example.data.utils.Resource.Success
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.domain.repositories.MainRepository
import com.example.mergeanimalsandflowers.data.local.LocalDataSource
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.data.remote.dto.AnimalDto
import com.example.mergeanimalsandflowers.data.remote.dto.FlowerDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainRepositoryImpTest {

    // test subject
    lateinit var mainRepository: MainRepository

    // Collaborators
    lateinit var localDataSource: LocalDataSource
    lateinit var remoteDataSource: RemoteDataSource
    lateinit var mainRepositoryMapper: MainRepositoryMapper

    // Utilities
    lateinit var listOfAnimalDto: ArrayList<AnimalDto>
    lateinit var listOfFlowerDto: ArrayList<FlowerDto>
    lateinit var mainAnimalResponseDto: MainAnimalResponseDto
    lateinit var mainFlowerResponseDto: MainFlowerResponseDto
    lateinit var listOfMergedEntities: ArrayList<AnimalAndFlowerMergedEntity>

    @Before
    fun setup() = runBlockingTest {

        mainRepositoryMapper = MainRepositoryMapper()

        // remote
        remoteDataSource = mock()
        listOfAnimalDto = arrayListOf()
        listOfAnimalDto.add(AnimalDto(1, "anim1", ""))
        listOfAnimalDto.add(AnimalDto(2, "anim2", ""))
        mainAnimalResponseDto = MainAnimalResponseDto(listOfAnimalDto)
        whenever(remoteDataSource.getAnimals()).thenReturn(flow { emit(mainAnimalResponseDto) })

        listOfFlowerDto = arrayListOf()
        listOfFlowerDto.add(FlowerDto(1, "flower1", ""))
        listOfFlowerDto.add(FlowerDto(2, "flower2", ""))
        mainFlowerResponseDto = MainFlowerResponseDto(listOfFlowerDto)
        whenever(remoteDataSource.getFlowers()).thenReturn(flow { emit(mainFlowerResponseDto) })

        // local
        localDataSource = mock()
        listOfMergedEntities = arrayListOf()
        listOfMergedEntities.add(AnimalAndFlowerMergedEntity("anim1", "flower1", "", "", arrayListOf()))
        listOfMergedEntities.add(AnimalAndFlowerMergedEntity("anim2", "flower2", "", "", arrayListOf()))
        whenever(localDataSource.getAllMergedAnimalFlower()).thenReturn(flow { emit(listOfMergedEntities) })
        whenever(localDataSource.getLastUpdateMergedItems()).thenReturn(System.currentTimeMillis())

        mainRepository = MainRepositoryImp(localDataSource, remoteDataSource, mainRepositoryMapper)
    }

    @Test
    fun getAllMergedModels_notForceRefresh() = runBlockingTest {
        mainRepository.getAllMergedAnimalFlower(false)
            .test {
                assertThat(awaitItem().data).isEqualTo(mainRepositoryMapper.mapEntitiesToModels(listOfMergedEntities))
                awaitComplete()
            }
    }

    @Test
    fun getAllMergedStates_notForceRefresh() = runBlockingTest {
        mainRepository.getAllMergedAnimalFlower(false).test {
            awaitItem()
            awaitComplete()
        }
        verify(localDataSource, times(1)).getAllMergedAnimalFlower()
        verify(remoteDataSource, never()).getAnimals()
        verify(remoteDataSource, never()).getFlowers()
    }




}
