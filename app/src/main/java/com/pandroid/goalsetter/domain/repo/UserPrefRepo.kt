package com.pandroid.goalsetter.domain.repo

import kotlinx.coroutines.flow.Flow

interface UserPrefRepo {
    suspend fun saveCredentials(email: String, password: String)
    suspend fun loadCredentials(): Pair<String, String>?

    fun isFirstTime(): Flow<Boolean>
    suspend fun setFirstTime(isFirstTime: Boolean)

}
