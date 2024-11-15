package com.pandroid.goalsetter.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pandroid.goalsetter.data.repo.NotificationHandlerImpl
import com.pandroid.goalsetter.domain.repo.NotificationHandler
import org.koin.dsl.module

val fireBaseModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseDatabase> { FirebaseDatabase.getInstance() }
    single<NotificationHandler> { NotificationHandlerImpl(context = get()) }
}