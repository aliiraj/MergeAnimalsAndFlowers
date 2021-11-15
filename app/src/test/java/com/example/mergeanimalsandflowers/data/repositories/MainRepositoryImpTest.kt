package com.example.mergeanimalsandflowers.data.repositories

import com.example.mergeanimalsandflowers.data.local.FakeLocalDataSource
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.data.remote.FakeRemoteDataSource
import com.example.mergeanimalsandflowers.data.remote.dto.AnimalDto
import com.example.mergeanimalsandflowers.data.remote.dto.FlowerDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import com.example.data.mapper.MainRepositoryMapper
import com.example.domain.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainRepositoryImpTest {

    private lateinit var mainRepositoryImp: MainRepositoryImp
    private lateinit var fakeLocalDataSource: FakeLocalDataSource
    private lateinit var fakeRemoteDataSource: FakeRemoteDataSource
    private lateinit var mainRepositoryMapper: MainRepositoryMapper

    private val item1 = AnimalAndFlowerMergedEntity("animal1", "flower1", "", "", emptyList())
    private val item2 = AnimalAndFlowerMergedEntity("animal2", "flower2", "", "", emptyList())
    private val items = mutableListOf(item1, item2)

    private val animalDto1 = AnimalDto(1, "animal1", "")
    private val animalDto2 = AnimalDto(2, "animal2", "")
    private val animalDtoList = mutableListOf(animalDto1, animalDto2)

    private val flowerDto1 = FlowerDto(1, "flower1", "")
    private val flowerDto2 = FlowerDto(2, "flower2", "")
    private val flowerDtoList = mutableListOf(flowerDto1, flowerDto2)



    @Before
    fun setup(){

        mainRepositoryMapper = MainRepositoryMapper()
        fakeLocalDataSource = FakeLocalDataSource()
        fakeRemoteDataSource = FakeRemoteDataSource(
            animalItem = MainAnimalResponseDto(animalDtoList),
            flowerItem = MainFlowerResponseDto(flowerDtoList)
        )

        mainRepositoryImp = MainRepositoryImp(
            localDataSource = fakeLocalDataSource,
            remoteDataSource = fakeRemoteDataSource,
            mainRepositoryMapper = mainRepositoryMapper
        )
    }

    @Test
    fun watchEvents_successState_forceRefresh() = runBlockingTest{
        mainRepositoryImp.getAllMergedAnimalFlower(true)
            .test {
                assertThat(awaitItem() is Resource.Loading).isTrue()
                assertThat(awaitItem() is Resource.Success).isTrue()
                awaitComplete()
            }
    }

    @Test
    fun watchData_successState_forceRefresh_haveData() = runBlockingTest{
        fakeLocalDataSource.insertMergedAnimalFlower(items)
        mainRepositoryImp.getAllMergedAnimalFlower(true)
            .test {
                // loading
                assertThat(awaitItem().data).isEqualTo(mainRepositoryMapper.mapEntitiesToModels(items))
                // success
                assertThat(awaitItem().data).isEqualTo(mainRepositoryMapper.mapEntitiesToModels(items))
                awaitComplete()
            }
    }

    @Test
    fun watchEvents_successState_notForceRefresh() = runBlockingTest{
        fakeLocalDataSource.saveLastUpdateMergedItems(System.currentTimeMillis())
        mainRepositoryImp.getAllMergedAnimalFlower(false)
            .test {
                assertThat(awaitItem() is Resource.Success).isTrue()
                awaitComplete()
            }
    }

    @Test
    fun watchData_successState_notForceRefresh_haveData() = runBlockingTest{
        fakeLocalDataSource.saveLastUpdateMergedItems(System.currentTimeMillis())
        fakeLocalDataSource.insertMergedAnimalFlower(items)
        mainRepositoryImp.getAllMergedAnimalFlower(false)
            .test {
                // success
                assertThat(awaitItem().data).isEqualTo(mainRepositoryMapper.mapEntitiesToModels(items))
                awaitComplete()
            }
    }

}