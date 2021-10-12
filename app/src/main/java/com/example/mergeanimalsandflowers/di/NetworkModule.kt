package com.example.mergeanimalsandflowers.di

import androidx.databinding.library.BuildConfig
import com.example.mergeanimalsandflowers.data.remote.apis.AnimalApis
import com.example.mergeanimalsandflowers.data.remote.apis.FlowerApis
import com.example.mergeanimalsandflowers.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideAnimalApis(retrofit: Retrofit): AnimalApis {
        return retrofit.create(AnimalApis::class.java)
    }

    @Singleton
    @Provides
    fun provideFlowerApis(retrofit: Retrofit): FlowerApis {
        return retrofit.create(FlowerApis::class.java)
    }
}