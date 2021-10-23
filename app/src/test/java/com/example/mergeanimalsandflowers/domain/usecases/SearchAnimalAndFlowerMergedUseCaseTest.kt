package com.example.mergeanimalsandflowers.domain.usecases

import app.cash.turbine.test
import com.example.mergeanimalsandflowers.domain.FakeMainRepository
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalTime
class SearchAnimalAndFlowerMergedUseCaseTest {

    private val item1 = AnimalAndFlowerMergedModel("animal1", "flower1", "", "", emptyList())
    private val item2 = AnimalAndFlowerMergedModel("animal2", "flower2", "", "", emptyList())
    private val items = mutableListOf(item1, item2)


    @Test
    fun responseSearchHaveData() = runBlockingTest{

        val fakeMainRepository = FakeMainRepository(items)
        val searchAnimalAndFlowerMergedUseCase = SearchAnimalAndFlowerMergedUseCase(fakeMainRepository)
        searchAnimalAndFlowerMergedUseCase.invoke("anim")
            .test {
                assertThat(awaitItem()).isEqualTo(items)
                awaitComplete()
            }
    }

    @Test
    fun responseSearchIsEmpty() = runBlockingTest{

        val fakeMainRepository = FakeMainRepository(items)
        val searchAnimalAndFlowerMergedUseCase = SearchAnimalAndFlowerMergedUseCase(fakeMainRepository)
        searchAnimalAndFlowerMergedUseCase.invoke("monk")
            .test {
                assertThat(awaitItem()).isEmpty()
                awaitComplete()
            }
    }
}