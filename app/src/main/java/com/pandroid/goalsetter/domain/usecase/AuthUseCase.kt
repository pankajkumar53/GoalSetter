package com.pandroid.goalsetter.domain.usecase

import com.pandroid.goalsetter.data.resource.Result
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.domain.model.UserModel
import com.pandroid.goalsetter.domain.repo.AuthRepo
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : AuthRepo {

    override suspend fun signUpUser(userModel: UserModel, password: String): State<Unit> {
        return authRepo.signUpUser(userModel, password)
    }

    override suspend fun updateUser(userModel: UserModel): State<Unit> {
        return authRepo.updateUser(userModel)
    }

    override suspend fun loginUser(email: String, password: String): State<Unit> {
        return authRepo.loginUser(email, password)
    }

    override suspend fun forgotPassword(email: String): State<Unit> {
        return authRepo.forgotPassword(email)
    }

    override suspend fun logOut(): State<Unit> {
        return authRepo.logOut()
    }

    override suspend fun getUserDetails(): Result<UserModel> {
        return authRepo.getUserDetails()
    }

}
