package com.pandroid.goalsetter.data.di

import com.pandroid.goalsetter.data.repo.GoalRepoImpl
import com.pandroid.goalsetter.domain.repo.GoalRepo
import com.pandroid.goalsetter.domain.usecase.GoalUseCase
import com.pandroid.goalsetter.presentation.screens.goal.GoalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val goalModule = module {

    // Goal instance
    single<GoalRepo> { GoalRepoImpl(get(), get()) }
    single<GoalUseCase> { GoalUseCase(get()) }

    // ViewModel
    viewModel<GoalViewModel> { GoalViewModel(get()) }

}