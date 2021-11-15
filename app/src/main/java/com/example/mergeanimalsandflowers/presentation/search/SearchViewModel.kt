package com.example.mergeanimalsandflowers.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.domain.usecases.SearchAnimalAndFlowerMergedUseCase
import com.example.mergeanimalsandflowers.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAnimalAndFlowerMergedUseCase: SearchAnimalAndFlowerMergedUseCase
) : ViewModel() {

    private var mutableSearchPageState = MutableStateFlow<SearchPageState>(SearchPageState.Initial)
    val searchPageState = mutableSearchPageState.asStateFlow()


    fun search(query: String) {
        if (query.isEmpty()) {
            mutableSearchPageState.value = SearchPageState.Initial
        } else {
            proceedSearch(query)
        }
    }

    private fun proceedSearch(query: String) {
        viewModelScope.launch {
            searchAnimalAndFlowerMergedUseCase(query)
                .collect { mutableSearchPageState.value = getSearchStateFromResult(it) }
        }
    }

    private fun getSearchStateFromResult(it: List<AnimalAndFlowerMergedModel>): SearchPageState {
        return if (it.isNullOrEmpty()) {
            SearchPageState.EmptyData(Constants.NO_ITEM_FOUND)
        } else {
            SearchPageState.DataFetched(it)
        }
    }
}