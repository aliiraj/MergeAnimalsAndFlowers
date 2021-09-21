package com.example.mergeanimalsandflowers.presentation.home

import com.example.mergeanimalsandflowers.data.models.AnimalAndFlowerMergedModel


sealed class HomePageState {
    object Loading : HomePageState()
    data class Error(val message: String) : HomePageState()
    data class EmptyData(val message: String) : HomePageState()
    data class DataFetched(val foods: List<AnimalAndFlowerMergedModel>) : HomePageState()
}