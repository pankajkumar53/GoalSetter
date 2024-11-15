package com.pandroid.goalsetter.data.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pandroid.goalsetter.domain.model.GoalModel
import com.pandroid.goalsetter.domain.repo.GoalRepo
import com.pandroid.goalsetter.utils.NodeRef
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoalRepoImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
) : GoalRepo {
    private val userId = firebaseAuth.currentUser?.uid
    override suspend fun createGoal(goalModel: GoalModel): Result<Unit> {
        return try {
            val goalId = firebaseDatabase.getReference(NodeRef.GOAL_REF).push().key
                ?: throw IllegalStateException("Unable to generate goal ID")

            // Create the GoalModel object
            val goal = goalModel.copy(goalId = goalId)

            // Save the product to Firebase Realtime Database
            val reference = userId?.let { firebaseDatabase.getReference(NodeRef.GOAL_REF).child(it).child(goalId) }
            reference?.setValue(goal)?.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadGoal(): Result<List<GoalModel>> {
        return try {
            /* Fetch the data from the GOAL_REF in Firebase*/
            val goalRef = userId?.let { firebaseDatabase.getReference(NodeRef.GOAL_REF).child(it) }
            val snapshot = goalRef?.get()?.await()

            /* Convert snapshot to list of ProductModel*/
            val goalList = mutableListOf<GoalModel>()
            if (snapshot != null) {
                for (goalSnapshot in snapshot.children) {
                    val goal = goalSnapshot.getValue(GoalModel::class.java)
                    goal?.let { goalList.add(it) }
                }
            }

            /* Return the list of products*/
            Result.success(goalList)
        } catch (e: Exception) {
            /* In case of error, return failure result with the exception*/
            Result.failure(e)
        }
    }

    override suspend fun updateGoal(goalModel: GoalModel): Result<Unit> {
        Log.d("UPDATEGOALDATA", "updateGoal: $goalModel")
        return try {
            // Fetch the current product details from Firebase
            val reference = userId?.let { firebaseDatabase.getReference(NodeRef.GOAL_REF).child(it).child(goalModel.goalId) }
            val goalSnapshot = reference?.get()?.await()

            if (goalSnapshot != null) {
                if (!goalSnapshot.exists()) {
                    throw IllegalStateException("Goal with ID ${goalModel.goalId} does not exist.")
                }
            }

            // Convert snapshot to GoalModel
            val currentGoal = goalSnapshot?.getValue(GoalModel::class.java)
                ?: throw IllegalStateException("Unable to parse goal data.")

            // Update the GoalModel object with new values
            val updatedGoal = currentGoal.copy(
                goalId = goalModel.goalId,
                title = goalModel.title,
                description = goalModel.description

            )
            Log.d("UPDATEGOALDATA", "updateGoalData: $updatedGoal")
            // Save the updated goal to Firebase Realtime Database
            reference.setValue(updatedGoal).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGoal(goalId: String): Result<Unit> {
        return try {
            // Reference to the Goal in the Firebase Realtime Database
            val goalRef = userId?.let { firebaseDatabase.getReference(NodeRef.GOAL_REF).child(it).child(goalId) }

            // Fetch the Goal details
            val goalSnapshot = goalRef?.get()?.await()

            if (goalSnapshot != null) {
                if (!goalSnapshot.exists()) {
                    throw IllegalStateException("Goal with ID $goalId does not exist.")
                }
            }

            // Delete the Goal entry from Firebase Realtime Database
            goalRef?.removeValue()?.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}