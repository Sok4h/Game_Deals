package com.sok4h.game_deals.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val AUTO_START_KEY = booleanPreferencesKey("hasAutoStartBeenShown")
class PreferencesRepository (
    private val datastore: DataStore<Preferences>
    ) {

     suspend fun savePreference(value: Boolean) {
         datastore.edit { preferences ->
             preferences[AUTO_START_KEY] = value
         }
    }
     suspend fun getPreference(): Flow<Boolean> {

        return datastore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val value = preferences[AUTO_START_KEY] ?: false
            value
        }
    }


}