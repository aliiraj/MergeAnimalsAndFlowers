package com.example.mergeanimalsandflowers.presentation.search

import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel


sealed class SearchPageState {
    object Initial : SearchPageState()
    data class EmptyData(val message: String) : SearchPageState()
    data class DataFetched(val mergedList: List<AnimalAndFlowerMergedModel>) : SearchPageState()
}