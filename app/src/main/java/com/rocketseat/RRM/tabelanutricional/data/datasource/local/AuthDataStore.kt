package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class AuthDataStore(private val context: Context) {
    companion object {
        private val LOGGED_IN_USER_EMAIL = stringPreferencesKey("logged_in_user_email")
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
        private val LAST_LOGIN_EMAIL = stringPreferencesKey("last_login_email")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LOGGED_IN_USER_EMAIL]?.isNotEmpty() == true
    }

    val loggedInUserEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LOGGED_IN_USER_EMAIL]
    }

    val rememberMe: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME] ?: false
    }

    val lastLoginEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LAST_LOGIN_EMAIL]
    }

    suspend fun setLoggedInUser(email: String, rememberMe: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_EMAIL] = email
            preferences[REMEMBER_ME] = rememberMe
            if (rememberMe) {
                preferences[LAST_LOGIN_EMAIL] = email
            }
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_EMAIL] = ""
            preferences[REMEMBER_ME] = false
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

