package com.example.mergeanimalsandflowers.di

import android.content.Context
import androidx.room.Room
import com.example.mergeanimalsandflowers.data.local.db.MyAppDatabase
import com.example.mergeanimalsandflowers.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideMyAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyAppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMergeAnimalFlowerDao(dataBase: MyAppDatabase) = dataBase.mergeAnimalFlowerDao

}