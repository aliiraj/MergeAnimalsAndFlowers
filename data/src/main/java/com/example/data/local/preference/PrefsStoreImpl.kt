package com.example.mergeanimalsandflowers.data.local.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mergeanimalsandflowers.data.local.preference.PrefsStoreImpl.PreferencesKeys.LAST_UPDATE_ITEMS
import com.example.mergeanimalsandflowers.utils.Constants.USER_PREFERENCES
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES)

class PrefsStoreImpl constructor(
    context: Context
): PrefsStore {

    private object PreferencesKeys {
        val LAST_UPDATE_ITEMS = longPreferencesKey("LAST_UPDATE_MERGED_ITEMS")
    }

    private val userPreferences = context.dataStore

    override suspend fun saveLastUpdateMergedItems(timestamp: Long) {
        userPreferences.edit { preferences ->
            preferences[LAST_UPDATE_ITEMS] = timestamp
        }
    }

    override suspend fun getLastUpdateMergedItems(): Long? {
        return userPreferences.data
            .map { it[LAST_UPDATE_ITEMS] }
            .first()
    }
}