package com.example.mergeanimalsandflowers.domain.usecases

import com.example.mergeanimalsandflowers.domain.repositories.MainRepository
import javax.inject.Inject


class GetAnimalAndFlowerMergedUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(forceToRefresh: Boolean) = mainRepository.getAllMergedAnimalFlower(forceToRefresh)

}