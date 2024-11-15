package com.pandroid.goalsetter.domain.usecase

import com.pandroid.goalsetter.domain.model.GoalModel
import com.pandroid.goalsetter.domain.repo.GoalRepo
import javax.inject.Inject

class GoalUseCase @Inject constructor(private val goalRepo: GoalRepo) : GoalRepo {
    override suspend fun createGoal(goalModel: GoalModel): Result<Unit> {
        return goalRepo.createGoal(goalModel)
    }

    override suspend fun loadGoal(): Result<List<GoalModel>> {
        return goalRepo.loadGoal()
    }

    override suspend fun updateGoal(goalModel: GoalModel): Result<Unit> {
        return goalRepo.updateGoal(goalModel)
    }

    override suspend fun deleteGoal(goalId: String): Result<Unit> {
        return goalRepo.deleteGoal(goalId)
    }

}