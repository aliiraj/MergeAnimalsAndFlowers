package com.example.mergeanimalsandflowers.data.repositories

import com.example.mergeanimalsandflowers.data.local.LocalDataSource
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.data.remote.RemoteDataSource
import com.example.mergeanimalsandflowers.data.remote.dto.AnimalDto
import com.example.mergeanimalsandflowers.data.remote.dto.FlowerDto
import com.example.mergeanimalsandflowers.domain.mapper.MainRepositoryMapper
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.domain.repositories.MainRepository
import com.example.mergeanimalsandflowers.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainRepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private var mainRepositoryMapper: MainRepositoryMapper
): MainRepository {

    override suspend fun getAllMergedAnimalFlower(forceToRefresh: Boolean) = networkBoundResource(
        query = { localDataSource.getAllMergedAnimalFlower().map { mainRepositoryMapper.mapEntitiesToModels(it) } },
        fetchAnimal = { remoteDataSource.getAnimals() },
        fetchFlower = { remoteDataSource.getFlowers() },
        mergeAnimalFlower = { mainAnimalResponseDto, mainFlowerResponseDto ->
            getCombination(mainAnimalResponseDto.data, mainFlowerResponseDto.data)
        },
        saveFetchResult = {
            localDataSource.saveLastUpdateMergedItems(System.currentTimeMillis())
            localDataSource.insertMergedAnimalFlower(it)
        },
        shouldFetch = {
            val lastTimeUpdate = localDataSource.getLastUpdateMergedItems()
            if (forceToRefresh || lastTimeUpdate == null) {
                return@networkBoundResource true
            }

            checkNeedUpdate(
                lastUpdate = lastTimeUpdate,
                timeExpire = TimeUnit.DAYS.toMillis(1)
            )
        }
    )

    override fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedModel>> {
        return localDataSource.searchInMergedAnimalFlower(query).map { mainRepositoryMapper.mapEntitiesToModels(it) }
    }

    private fun getCombination(
        animals: List<AnimalDto>?,
        flowers: List<FlowerDto>?
    ): List<AnimalAndFlowerMergedEntity> {

        if (animals.isNullOrEmpty() || flowers.isNullOrEmpty()) {
            return emptyList()
        }

        val animalsAndFlowersList = arrayListOf<AnimalAndFlowerMergedEntity>()

        animals.forEach { animal ->
            flowers.forEach { flower ->
                val animalHashSet = createHashAndRemoveSpace(animal.name)
                val flowerHashSet = createHashAndRemoveSpace(flower.name)
                // keep common chars and remove uncommon
                animalHashSet.retainAll(flowerHashSet)
                if (animalHashSet.isNotEmpty()) {
                    animalsAndFlowersList.add(
                        createAnimalAndFlowerMergedModel(
                            animal,
                            flower,
                            animalHashSet
                        )
                    )
                }
            }
        }
        return animalsAndFlowersList
    }

    private fun createHashAndRemoveSpace(name: String) = HashSet<Char>().apply {
        addAll(name.replace(" ", "").toCharArray().asList())
    }

    private fun createAnimalAndFlowerMergedModel(
        animal: AnimalDto,
        flower: FlowerDto,
        charList: HashSet<Char>
    ): AnimalAndFlowerMergedEntity {
        return AnimalAndFlowerMergedEntity(
            animalName = animal.name,
            flowerName = flower.name,
            animalImage = animal.image,
            flowerImage = flower.image,
            commonChars = charList.toList()
        )
    }

    private fun checkNeedUpdate(lastUpdate: Long, timeExpire: Long): Boolean {
        return lastUpdate < System.currentTimeMillis() - timeExpire
    }
}