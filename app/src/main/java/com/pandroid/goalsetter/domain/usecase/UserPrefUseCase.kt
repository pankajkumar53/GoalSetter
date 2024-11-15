package com.pandroid.goalsetter.domain.usecase

import com.pandroid.goalsetter.domain.repo.UserPrefRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPrefUseCase @Inject constructor(private val userPrefRepo: UserPrefRepo) : UserPrefRepo {

    // Save credentials
    override suspend fun saveCredentials(email: String, password: String) =
        userPrefRepo.saveCredentials(email, password)

    // Load credentials
    override suspend fun loadCredentials(): Pair<String, String>? = userPrefRepo.loadCredentials()

    // Check if it's the user's first time
    override fun isFirstTime(): Flow<Boolean> = userPrefRepo.isFirstTime()

    // Set first-time flag after onboarding or first launch
    override suspend fun setFirstTime(isFirstTime: Boolean) = userPrefRepo.setFirstTime(isFirstTime)

}

