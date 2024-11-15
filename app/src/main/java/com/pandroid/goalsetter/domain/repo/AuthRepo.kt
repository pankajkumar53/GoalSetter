package com.pandroid.goalsetter.domain.repo

import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.data.resource.Result
import com.pandroid.goalsetter.domain.model.UserModel

interface AuthRepo {
    suspend fun signUpUser(userModel: UserModel, password: String): State<Unit>
    suspend fun updateUser(userModel: UserModel): State<Unit>
    suspend fun loginUser(email: String, password: String): State<Unit>
    suspend fun forgotPassword(email: String): State<Unit>
    suspend fun logOut(): State<Unit>
    suspend fun getUserDetails(): Result<UserModel>
}