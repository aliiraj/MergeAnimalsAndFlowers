package com.example.mergeanimalsandflowers.utils

import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
inline fun <ResultType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetchAnimal: suspend () -> Flow<MainAnimalResponseDto>,
    crossinline fetchFlower: suspend () -> Flow<MainFlowerResponseDto>,
    crossinline mergeAnimalFlower: suspend (MainAnimalResponseDto, MainFlowerResponseDto) -> List<AnimalAndFlowerMergedEntity>,
    crossinline saveFetchResult: suspend (List<AnimalAndFlowerMergedEntity>) -> Unit,
    crossinline shouldFetch: suspend () -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch()) {

        emit(Resource.Loading(data))

        try {
            fetchAnimal().zip(fetchFlower()){ animals, flowers ->
                saveFetchResult(mergeAnimalFlower(animals, flowers))
            }.collect()
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}