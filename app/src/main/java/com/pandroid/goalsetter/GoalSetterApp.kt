package com.pandroid.goalsetter

import android.app.Application
import com.google.firebase.FirebaseApp
import com.pandroid.goalsetter.data.di.authModule
import com.pandroid.goalsetter.data.di.fireBaseModule
import com.pandroid.goalsetter.data.di.goalModule
import com.pandroid.goalsetter.data.di.userPrefModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GoalSetterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(this@GoalSetterApp)

            modules(userPrefModule, authModule, fireBaseModule, goalModule)

        }

    }
}