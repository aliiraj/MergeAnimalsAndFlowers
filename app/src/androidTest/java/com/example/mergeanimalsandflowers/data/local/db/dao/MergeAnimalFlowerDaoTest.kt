package com.example.mergeanimalsandflowers.data.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mergeanimalsandflowers.data.local.db.MyAppDatabase
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MergeAnimalFlowerDaoTest {

    private val item1 = AnimalAndFlowerMergedEntity("animal1", "flower1", "", "", emptyList())
    private val item2 = AnimalAndFlowerMergedEntity("animal2", "flower2", "", "", emptyList())
    private val items = listOf(item1, item2)

    private lateinit var database: MyAppDatabase
    private lateinit var dao: MergeAnimalFlowerDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MyAppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.mergeAnimalFlowerDao
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertMergeAnimalFlowerTest() = runBlockingTest {

        dao.insertMergedAnimalFlower(items)

        val itemsFromDb = dao.getAllMergedAnimalFlower().first()
        assertThat(items).isEqualTo(itemsFromDb)
    }

    @Test
    fun searchInMergedAnimalFlower_returnEmpty() = runBlockingTest {

        dao.insertMergedAnimalFlower(items)

        val items = dao.searchInMergedAnimalFlower("monkey").first()

        assertThat(items).isEmpty()
    }

    @Test
    fun searchInMergedAnimalFlower_returnNotEmpty() = runBlockingTest {

        dao.insertMergedAnimalFlower(items)

        val items = dao.searchInMergedAnimalFlower("anim").first()

        assertThat(items).isNotEmpty()
    }

    @Test
    fun searchInMergedAnimalFlower_resultContainsQuery() = runBlockingTest {

        dao.insertMergedAnimalFlower(items)

        val dbItems = dao.searchInMergedAnimalFlower("anim").first()

        assertThat(items).isEqualTo(dbItems)
    }



}