package com.example.mergeanimalsandflowers.utils

data class Resource<out T>(val status: NetworkState, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(NetworkState.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(NetworkState.ERROR, data, msg)
        }
    }
}