package com.example.mergeanimalsandflowers.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MergeAnimalFlowerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMergedAnimalFlower(items: List<AnimalAndFlowerMergedEntity>)

    @Query("SELECT * FROM mergedEntity ")
    fun getAllMergedAnimalFlower(): Flow<List<AnimalAndFlowerMergedEntity>>

    @Query(
        """
            SELECT * FROM mergedEntity
            WHERE animalName LIKE '%' || :query || '%'
            OR flowerName LIKE '%' || :query || '%' 
            """
    )
    fun searchInMergedAnimalFlower(query: String): Flow<List<AnimalAndFlowerMergedEntity>>

}