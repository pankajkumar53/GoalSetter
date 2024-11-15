package com.pandroid.goalsetter.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.pandroid.goalsetter.data.repo.UserPrefRepoImpl
import com.pandroid.goalsetter.domain.repo.UserPrefRepo
import com.pandroid.goalsetter.domain.usecase.UserPrefUseCase
import com.pandroid.goalsetter.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

val userPrefModule = module {

    // UserPrefRepo and UseCase
    single<UserPrefRepo> { UserPrefRepoImpl(get()) }
    single { UserPrefUseCase(get()) }

    // User Pref instance
    single<DataStore<Preferences>> {
        val context: Context = androidContext()
        context.dataStore
    }

    // Splash ViewModel
    viewModel { SplashViewModel(get()) }

}

