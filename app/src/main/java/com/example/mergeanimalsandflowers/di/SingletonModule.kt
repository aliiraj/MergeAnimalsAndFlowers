package com.example.mergeanimalsandflowers.di

import android.content.Context
import com.example.mergeanimalsandflowers.data.local.LocalDataSource
import com.example.mergeanimalsandflowers.data.local.LocalDataSourceImp
import com.example.mergeanimalsandflowers.data.local.db.dao.MergeAnimalFlowerDao
import com.example.mergeanimalsandflowers.data.local.preference.PrefsStore
import com.example.mergeanimalsandflowers.data.local.preference.PrefsStoreImpl
import com.example.mergeanimalsandflowers.data.remote.RemoteDataSource
import com.example.mergeanimalsandflowers.data.remote.RemoteDataSourceImp
import com.example.mergeanimalsandflowers.data.remote.apis.AnimalApis
import com.example.mergeanimalsandflowers.data.remote.apis.FlowerApis
import com.example.mergeanimalsandflowers.data.repositories.MainRepositoryImp
import com.example.mergeanimalsandflowers.domain.mapper.MainRepositoryMapper
import com.example.mergeanimalsandflowers.domain.repositories.MainRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

    @Singleton
    @Provides
    fun provideViewPump(calligraphyInterceptor: CalligraphyInterceptor) = ViewPump.builder()
        .addInterceptor(calligraphyInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideCalligraphyInterceptor(calligraphyConfig: CalligraphyConfig) =
        CalligraphyInterceptor(calligraphyConfig)

    @Singleton
    @Provides
    fun provideCalligraphyConfig() = CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/iransanswebmedium.ttf")
        .build()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        dao: MergeAnimalFlowerDao,
        prefsStore: PrefsStore
    ): LocalDataSource {
        return LocalDataSourceImp(dao, prefsStore)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        animalApis: AnimalApis,
        flowerApis: FlowerApis
    ): RemoteDataSource {
        return RemoteDataSourceImp(animalApis, flowerApis)
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        mainRepositoryMapper: MainRepositoryMapper
    ): MainRepository {
        return MainRepositoryImp(localDataSource, remoteDataSource, mainRepositoryMapper)
    }

    @Singleton
    @Provides
    fun providePrefsStore(
        @ApplicationContext context: Context
    ): PrefsStore {
        return PrefsStoreImpl(context)
    }

    @Singleton
    @Provides
    fun provideMainRepositoryMapper() = MainRepositoryMapper()
}