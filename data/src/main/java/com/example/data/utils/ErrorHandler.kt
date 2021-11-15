package com.example.mergeanimalsandflowers.utils

import com.example.mergeanimalsandflowers.utils.Constants.ERROR_NO_CONNECTION
import com.example.mergeanimalsandflowers.utils.Constants.ERROR_SERVER
import com.example.mergeanimalsandflowers.utils.Constants.ERROR_UNKNOWN
import retrofit2.HttpException
import java.io.IOException


object ErrorHandler {

    fun getErrorMessage(error: Throwable?): String {
        return when(error) {
            is IOException -> ERROR_NO_CONNECTION
            is HttpException -> ERROR_SERVER
            else -> ERROR_UNKNOWN
        }
    }

}