package com.example.mergeanimalsandflowers.data.local.preference


interface PrefsStore {
    suspend fun saveLastUpdateMergedItems(timestamp: Long)
    suspend fun getLastUpdateMergedItems(): Long?
}