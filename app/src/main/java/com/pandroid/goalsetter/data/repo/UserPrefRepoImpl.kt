package com.pandroid.goalsetter.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pandroid.goalsetter.domain.repo.UserPrefRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPrefRepo {

    private val emailKey = stringPreferencesKey("email")
    private val passwordKey = stringPreferencesKey("password")
    private val firstTimeKey = booleanPreferencesKey("first_time")

    override suspend fun saveCredentials(email: String, password: String) {

        dataStore.edit { settings ->
            settings[emailKey] = email
            settings[passwordKey] = password
        }
    }

    override suspend fun loadCredentials(): Pair<String, String>? {
        val preferences = dataStore.data.first()
        return preferences[emailKey]?.let { email ->
            preferences[passwordKey]?.let { password ->
                Pair(email, password)
            }
        }
    }


    // Check if it's the user's first time using the app
    override fun isFirstTime(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[firstTimeKey] ?: true
        }
    }

    // Set the first-time flag after the app has been opened
    override suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[firstTimeKey] = isFirstTime
        }
    }

}