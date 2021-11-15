package com.example.mergeanimalsandflowers.domain.usecases

import com.example.domain.usecases.GetAnimalAndFlowerMergedUseCase
import com.example.mergeanimalsandflowers.domain.FakeMainRepository
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.example.domain.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalTime
class GetAnimalAndFlowerMergedUseCaseTest {

    private val item1 = AnimalAndFlowerMergedModel("animal1", "flower1", "", "", emptyList())
    private val item2 = AnimalAndFlowerMergedModel("animal2", "flower2", "", "", emptyList())
    private val items = mutableListOf(item1, item2)

    @Test
    fun watchEvents_successState_forceRefresh() = runBlockingTest{

        val fakeMainRepository = FakeMainRepository(mutableListOf())
        val getAnimalAndFlowerMergedUseCase = GetAnimalAndFlowerMergedUseCase(fakeMainRepository)
        getAnimalAndFlowerMergedUseCase.invoke(forceToRefresh = true)
            .test {
                assertThat(awaitItem() is Resource.Loading).isTrue()
                assertThat(awaitItem() is Resource.Success).isTrue()
                awaitComplete()
            }
    }

    @Test
    fun watchData_successState_forceRefresh_haveData() = runBlockingTest{

        val fakeMainRepository = FakeMainRepository(items)
        val getAnimalAndFlowerMergedUseCase = GetAnimalAndFlowerMergedUseCase(fakeMainRepository)
        getAnimalAndFlowerMergedUseCase.invoke(forceToRefresh = true)
            .test {
                // loading
                assertThat(awaitItem().data).isEqualTo(items)
                // success
                assertThat(awaitItem().data).isEqualTo(items)
                awaitComplete()
            }
    }

    @Test
    fun watchEvent_errorState_forceRefresh_haveData() = runBlockingTest{

        val fakeMainRepository = FakeMainRepository(items)
        fakeMainRepository.isError = true
        val getAnimalAndFlowerMergedUseCase = GetAnimalAndFlowerMergedUseCase(fakeMainRepository)
        getAnimalAndFlowerMergedUseCase.invoke(forceToRefresh = true)
            .test {
                // loading
                assertThat(awaitItem() is Resource.Loading).isEqualTo(true)
                // error
                assertThat(awaitItem() is Resource.Error).isEqualTo(true)
                awaitComplete()
            }
    }

}