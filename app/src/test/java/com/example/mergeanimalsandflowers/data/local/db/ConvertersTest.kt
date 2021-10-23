package com.example.mergeanimalsandflowers.data.local.db


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test



class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setup(){
        converters = Converters()
    }

    @Test
    fun fromStringToCharList() {
        val string = "[\"s\",\"d\"]"

        val chars = converters.fromStringToCharList(string)
        assertThat(chars).isEqualTo(listOf('s', 'd'))
    }

    @Test
    fun fromCharListToString() {
        val chars = listOf('s', 'd')

        val string = converters.fromCharListToString(chars)

        assertThat("[\"s\",\"d\"]").isEqualTo(string)
    }
}