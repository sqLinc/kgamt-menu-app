package com.kgamt.menu.app.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    val LANGUAGE = stringPreferencesKey("language")
    val TOKEN = stringPreferencesKey("token")
    val GROUP = stringPreferencesKey("group")


}

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val isDarkThemeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] ?: false
        }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDark
        }
    }

    val language: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LANGUAGE] ?: "en"
        }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language
        }
    }

    val token: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.TOKEN]
        }

    suspend fun setToken(token: String){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    suspend fun deleteToken(){
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
        }
    }

    val group: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.GROUP]
        }

    suspend fun setGroup(group: String){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.GROUP] = group
        }
    }

    suspend fun deleteGroup(){
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.GROUP)
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.TOKEN]
            }
            .firstOrNull()
    }





}