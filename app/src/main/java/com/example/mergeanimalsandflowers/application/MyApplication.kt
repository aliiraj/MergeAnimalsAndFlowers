package com.example.mergeanimalsandflowers.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var viewPump: ViewPump

    override fun onCreate() {
        super.onCreate()

        configFont()
    }

    private fun configFont() {
        ViewPump.init(viewPump)
    }
}