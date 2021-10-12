package com.example.mergeanimalsandflowers.data.local

import com.example.mergeanimalsandflowers.data.local.db.dao.MergeAnimalFlowerDao
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import com.example.mergeanimalsandflowers.data.local.preference.PrefsStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val dao: MergeAnimalFlowerDao,
    private val prefsStore: PrefsStore
): LocalDataSource {

    override suspend fun insertMergedAnimalFlower(items: List<AnimalAndFlowerMergedEntity>) {
        dao.insertMergedAnimalFlower(items)
    }

    override fun getAllMergedAnimalFlower(): Flow<List<AnimalAndFlowerMergedEntity>> {
        return dao.getAllMergedAnimalFlower()
    }

    override fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedEntity>> {
        return dao.searchInMergedAnimalFlower(query)
    }

    override suspend fun saveLastUpdateMergedItems(timestamp: Long) {
        prefsStore.saveLastUpdateMergedItems(timestamp)
    }

    override suspend fun getLastUpdateMergedItems(): Long? {
        return prefsStore.getLastUpdateMergedItems()
    }

}