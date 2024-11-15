package com.pandroid.goalsetter.presentation.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.domain.model.GoalModel
import com.pandroid.goalsetter.domain.usecase.GoalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoalViewModel @Inject constructor(private val goalUseCase: GoalUseCase) : ViewModel() {

    private val _state = MutableStateFlow<State<Any>>(State.Idle())
    val state: StateFlow<State<Any>> = _state

    private val _goalList = MutableStateFlow<List<GoalModel>>(emptyList())
    val goalList: StateFlow<List<GoalModel>> = _goalList

    private var isGoalUpdated = false


    fun createGoal(
        goalModel: GoalModel
    ) {
        viewModelScope.launch {
            _state.value = State.Loading()
            try {
                val result = goalUseCase.createGoal(goalModel)
                if (result.isSuccess) {
                    _state.value = State.Success(Unit)
                } else {
                    _state.value = State.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "An unexpected error occurred")
            }
        }

    }

    fun loadGoal(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = State.Loading()
            val result = goalUseCase.loadGoal()
            val goals = result.getOrNull() ?: emptyList()
            _goalList.value = goals
            _state.value = State.Success(goals)
        }
    }

    fun updateGoal(
        goalModel: GoalModel
    ) {
        viewModelScope.launch {
            _state.value = State.Loading()
            try {
                val result = goalUseCase.updateGoal(goalModel)
                if (result.isSuccess) {
                    isGoalUpdated = true
                    _state.value = State.Success(Unit)
                    loadGoal()
                } else {
                    _state.value = State.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }


    fun deleteGoal(goalId: String) {
        viewModelScope.launch {
            _state.value = State.Loading()
            try {
                goalUseCase.deleteGoal(goalId)
                loadGoal()
                _state.value = State.Success(Unit)
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to delete goal")
            }
        }
    }


    fun resetGoalUpdatedFlag() {
        isGoalUpdated = false
    }

    fun isGoalRecentlyUpdated() = isGoalUpdated


}