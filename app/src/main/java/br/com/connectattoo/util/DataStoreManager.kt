package br.com.connectattoo.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

object DataStoreManager {
    suspend fun saveToken(context: Context, key: String, value: String) {

        val wrappedKey = stringPreferencesKey(key)
        context.userSettingsDataStore.edit {
            it[wrappedKey] = value
        }

    }

    suspend fun getStringToken(context: Context, key: String): String {

        return context.userSettingsDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }.firstOrNull() ?: ""

    }

    suspend fun deleteApiKey(context: Context, key: String) {
        val wrappedKey = stringPreferencesKey(key)
        context.userSettingsDataStore.edit {
            it.remove(wrappedKey)
        }
    }
}
