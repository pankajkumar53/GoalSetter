package com.pandroid.goalsetter.domain.repo

import com.pandroid.goalsetter.domain.model.GoalModel

interface GoalRepo {
    suspend fun createGoal(
        goalModel: GoalModel
    ): Result<Unit>

    suspend fun loadGoal(): Result<List<GoalModel>>

    suspend fun updateGoal(
       goalModel: GoalModel
    ): Result<Unit>

    suspend fun deleteGoal(goalId: String): Result<Unit>

}