package com.example.mergeanimalsandflowers.utils

import com.example.mergeanimalsandflowers.utils.Constants.ERROR_NO_CONNECTION
import com.example.mergeanimalsandflowers.utils.Constants.ERROR_SERVER
import com.example.mergeanimalsandflowers.utils.Constants.ERROR_UNKNOWN
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(call: suspend () -> T): Resource<T> {
    return try {
        Resource.success(call())
    } catch (e: Exception) {
        when (e) {
            is HttpException -> Resource.error(ERROR_SERVER,null)
            is IOException -> Resource.error(ERROR_NO_CONNECTION,null)
            else -> Resource.error(ERROR_UNKNOWN,null)
        }
    }
}