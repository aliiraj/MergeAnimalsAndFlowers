package com.example.mergeanimalsandflowers.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mergeanimalsandflowers.data.local.db.dao.MergeAnimalFlowerDao
import com.example.mergeanimalsandflowers.data.local.db.entities.AnimalAndFlowerMergedEntity


@Database(
    entities = [AnimalAndFlowerMergedEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class MyAppDatabase : RoomDatabase() {

    abstract val mergeAnimalFlowerDao: MergeAnimalFlowerDao

}
