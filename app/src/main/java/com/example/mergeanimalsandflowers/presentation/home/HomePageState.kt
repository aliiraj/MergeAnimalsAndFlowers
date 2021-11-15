package com.example.mergeanimalsandflowers.presentation.home

import com.example.domain.models.AnimalAndFlowerMergedModel

sealed class HomePageState {
    object Initial: HomePageState()
    object Loading : HomePageState()
    data class Refreshing(val mergedList: List<AnimalAndFlowerMergedModel>) : HomePageState()
    data class Error(val message: String) : HomePageState()
    data class RefreshError(val message: String) : HomePageState()
    data class EmptyData(val message: String) : HomePageState()
    data class DataFetched(val mergedList: List<AnimalAndFlowerMergedModel>) : HomePageState()
}