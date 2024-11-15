package com.pandroid.goalsetter.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandroid.goalsetter.data.resource.Result
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.domain.model.UserModel
import com.pandroid.goalsetter.domain.usecase.AuthUseCase
import com.pandroid.goalsetter.domain.usecase.UserPrefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userPrefUseCase: UserPrefUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State<Any>>(State.Idle())
    val state: StateFlow<State<Any>> = _state

    private val _userState = MutableStateFlow<Result<UserModel>?>(null)
    val userState: StateFlow<Result<UserModel>?> = _userState

    // Auto-login function
    fun autoLogin() {
        viewModelScope.launch {
            val credentials = userPrefUseCase.loadCredentials()

            if (credentials != null) {
                loginUser(credentials.first, credentials.second)
            }

        }

    }

    // Sign-up function
    fun signUpUser(userModel: UserModel, password: String) {
        viewModelScope.launch {
            _state.value = State.Loading()

            // Switch to IO dispatcher for background work
            val result = withContext(Dispatchers.IO) {
                authUseCase.signUpUser(userModel, password)
            }

            // Switch back to Main dispatcher for UI update
            withContext(Dispatchers.Main) {
                when (result) {
                    is State.Success -> {
                        // Save credentials in IO dispatcher
                        withContext(Dispatchers.IO) {
                            userPrefUseCase.saveCredentials(userModel.email, password)
                        }
                        _state.value = result
                    }

                    is State.Error -> {
                        _state.value = result
                    }

                    else -> {
                        _state.value = State.Error("Unexpected state")
                    }
                }
            }
        }
    }

    // Update User
    fun update(userModel: UserModel) {
        viewModelScope.launch {
            val result = authUseCase.updateUser(userModel)
            when (result) {
                is State.Error -> {
                    _userState.value = Result.Failure(Exception("Unknown exception"))
                }
                is State.Success -> {
                    _userState.value = Result.Success(userModel)
                }
                else -> Unit
            }
        }

    }

    // Manual login function
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _state.value = State.Loading()

            // Perform the login operation on the IO dispatcher
            val result = withContext(Dispatchers.IO) {
                authUseCase.loginUser(email, password)
            }

            // Switch back to Main dispatcher for UI update
            withContext(Dispatchers.Main) {
                when (result) {
                    is State.Success -> {
                        _state.value = State.Success(result.data)
                        // Save credentials on IO dispatcher
                        withContext(Dispatchers.IO) {
                            userPrefUseCase.saveCredentials(email, password)
                        }
                    }

                    is State.Error -> {
                        _state.value = result
                    }

                    else -> {
                        _state.value = State.Error("Unexpected state during login")
                    }
                }
            }
        }
    }

    // Get User Details
    fun getUser() {
        viewModelScope.launch {
            _userState.value = authUseCase.getUserDetails()
        }
    }

    // Forgot password function
    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _state.value = State.Loading()
            when (val result = authUseCase.forgotPassword(email)) {
                is State.Success -> {
                    _state.value = result
                }

                is State.Error -> {
                    _state.value = result
                }

                else -> {
                    _state.value = State.Error("Unexpected state")
                }
            }
        }
    }

    // Logout user
    fun logout() {
        viewModelScope.launch {
            userPrefUseCase.saveCredentials("", "")
            authUseCase.logOut()
        }
    }

}






