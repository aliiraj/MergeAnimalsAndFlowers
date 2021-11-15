package com.example.mergeanimalsandflowers.presentation.details

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class DetailsFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }


    @Test
    fun testFirst(){
        assertTrue(true)

    }
}