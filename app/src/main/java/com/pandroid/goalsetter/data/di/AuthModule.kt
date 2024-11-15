package com.pandroid.goalsetter.data.di

import com.pandroid.goalsetter.data.repo.AuthRepoImpl
import com.pandroid.goalsetter.domain.repo.AuthRepo
import com.pandroid.goalsetter.domain.usecase.AuthUseCase
import com.pandroid.goalsetter.presentation.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    // Auth instance
    single<AuthRepo> { AuthRepoImpl(get(), get()) }
    single { AuthUseCase(get()) }

    viewModel<AuthViewModel> { AuthViewModel(get(), get()) }

}