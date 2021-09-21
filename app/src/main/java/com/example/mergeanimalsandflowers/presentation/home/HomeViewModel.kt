package com.example.mergeanimalsandflowers.presentation.home

import androidx.lifecycle.*
import com.example.mergeanimalsandflowers.data.models.*
import com.example.mergeanimalsandflowers.data.repositories.AnimalRepository
import com.example.mergeanimalsandflowers.data.repositories.FlowerRepository
import com.example.mergeanimalsandflowers.utils.Constants.ERROR_UNKNOWN
import com.example.mergeanimalsandflowers.utils.Constants.NO_ITEM_FOUND
import com.example.mergeanimalsandflowers.utils.NetworkState
import com.example.mergeanimalsandflowers.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
    private val flowerRepository: FlowerRepository
) : ViewModel() {

    private val mutableHomePageState = MutableLiveData<HomePageState>()
    val homePageState: LiveData<HomePageState> = mutableHomePageState

    init {
        getAnimalsAndFlowers()
    }

    fun getAnimalsAndFlowers() {
        viewModelScope.launch {
            mutableHomePageState.value = HomePageState.Loading
            animalRepository.getAnimals()
                .zip(flowerRepository.getFlowers()) { animals, flowers ->
                    if (isBothSuccessful(animals, flowers)) {
                        val combinedData = getCombination(animals.data?.data, flowers.data?.data)
                        if (combinedData.isNotEmpty()) {
                            return@zip HomePageState.DataFetched(combinedData)
                        }
                        return@zip HomePageState.EmptyData(NO_ITEM_FOUND)
                    }
                    HomePageState.Error(getErrorMessage(animals, flowers) ?: ERROR_UNKNOWN)
                }
                .flowOn(Dispatchers.IO)
                .collect { homePageStateSuccessOrError ->
                    mutableHomePageState.value = homePageStateSuccessOrError
                }
        }
    }

    private fun getCombination(
        animals: List<AnimalModel>?,
        flowers: List<FlowerModel>?
    ): List<AnimalAndFlowerMergedModel> {

        if (animals.isNullOrEmpty() || flowers.isNullOrEmpty()) {
            return emptyList()
        }

        val animalsAndFlowersList = arrayListOf<AnimalAndFlowerMergedModel>()

        animals.forEach { animal ->
            flowers.forEach { flower ->
                val animalHashSet = createHashAndRemoveSpace(animal.name)
                val flowerHashSet = createHashAndRemoveSpace(flower.name)
                // keep common chars and remove uncommon
                animalHashSet.retainAll(flowerHashSet)
                if (animalHashSet.isNotEmpty()) {
                    animalsAndFlowersList.add(createAnimalAndFlowerMergedModel(animal, flower, animalHashSet))
                }
            }
        }
        return animalsAndFlowersList
    }

    private fun createHashAndRemoveSpace(name: String) = HashSet<Char>().apply {
        addAll(name.replace(" ", "").toCharArray().asList())
    }

    private fun createAnimalAndFlowerMergedModel(
        animal: AnimalModel,
        flower: FlowerModel,
        charList: HashSet<Char>
    ): AnimalAndFlowerMergedModel {
        return AnimalAndFlowerMergedModel(
            animalName = animal.name,
            flowerName = flower.name,
            animalImage = animal.image,
            flowerImage = flower.image,
            commonChars = charList.toList()
        )
    }

    private fun getErrorMessage(
        animals: Resource<MainAnimalResponseModel>,
        flowers: Resource<MainFlowerResponseModel>
    ) = if (animals.status == NetworkState.ERROR) animals.message else flowers.message

    private fun isBothSuccessful(
        animals: Resource<MainAnimalResponseModel>,
        flowers: Resource<MainFlowerResponseModel>
    ) = animals.status == NetworkState.SUCCESS && flowers.status == NetworkState.SUCCESS
}