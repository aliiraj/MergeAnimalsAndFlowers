package com.example.mergeanimalsandflowers.domain.usecases

import com.example.mergeanimalsandflowers.domain.repositories.MainRepository
import javax.inject.Inject


class SearchAnimalAndFlowerMergedUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(query: String) = mainRepository.searchInMergedAnimalFlower(query)
}