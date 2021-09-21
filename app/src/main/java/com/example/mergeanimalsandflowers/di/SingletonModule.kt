package com.example.mergeanimalsandflowers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}