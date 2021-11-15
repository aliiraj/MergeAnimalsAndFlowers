package com.example.mergeanimalsandflowers.presentation.details

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mergeanimalsandflowers.R
import com.example.domain.models.AnimalAndFlowerMergedModel
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule
import org.junit.Test



@MediumTest
@RunWith(AndroidJUnit4::class)
class DetailsFragmentAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Test
    fun firstTest(){

        // given
        val mergedAnimalFlower = AnimalAndFlowerMergedModel(
            "گراز",
            "رز",
            "",
            "",
             listOf('ر', 'ز')
        )

        // when
        val bundle = DetailsFragmentArgs(mergedAnimalFlower = mergedAnimalFlower).toBundle()
        launchFragmentInContainer<DetailsFragment>(bundle, R.style.Theme_MergeAnimalsAndFlowers)

        // then
        onView(withId(R.id.txtCommonCount)).check(matches(isDisplayed()))
        onView(withId(R.id.txtCommonCount)).check(matches(withText("2")))
        onView(withId(R.id.txtNames)).check(matches(withText("گراز / رز")))
    }
}