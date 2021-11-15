package com.example.mergeanimalsandflowers.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.domain.usecases.GetAnimalAndFlowerMergedUseCase
import com.example.mergeanimalsandflowers.utils.Constants
import com.example.mergeanimalsandflowers.utils.ErrorHandler
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimalAndFlowerMergedUseCase: GetAnimalAndFlowerMergedUseCase
) : ViewModel() {

    private val refreshTriggerChannel = Channel<Boolean>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    private var mutableHomePageState = MutableStateFlow<HomePageState>(HomePageState.Initial)
    val homePageState = mutableHomePageState.asStateFlow()

    init {
        fetchAnimalsAndFlowers()
        getAnimalsAndFlowers(false)
    }

    private fun fetchAnimalsAndFlowers() {
        viewModelScope.launch {
            refreshTrigger.flatMapLatest { forceToRefresh ->
                getAnimalAndFlowerMergedUseCase(forceToRefresh)
            }.collect { mutableHomePageState.value = getHomePageState(it) }
        }
    }

    fun getAnimalsAndFlowers(forceToRefresh: Boolean){
        viewModelScope.launch { refreshTriggerChannel.send(forceToRefresh) }
    }

    private fun getHomePageState(result: Resource<List<AnimalAndFlowerMergedModel>>): HomePageState {
        return when(result) {
            is Resource.Loading -> {
                if (result.data.isNullOrEmpty()){
                    HomePageState.Loading
                } else {
                    HomePageState.Refreshing(result.data!!)
                }
            }

            is Resource.Success -> {
                if (result.data.isNullOrEmpty()){
                    HomePageState.EmptyData(Constants.NO_ITEM_FOUND)
                } else {
                    HomePageState.DataFetched(result.data!!)
                }
            }

            else -> {
                val errorMessage = ErrorHandler.getErrorMessage(result.error)
                if (result.data.isNullOrEmpty()){
                    HomePageState.Error(errorMessage)
                } else {
                    HomePageState.RefreshError(errorMessage)
                }
            }
        }
    }

}