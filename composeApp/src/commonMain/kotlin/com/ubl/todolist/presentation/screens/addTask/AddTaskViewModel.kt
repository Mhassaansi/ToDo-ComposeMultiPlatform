package com.ubl.todolist.presentation.screens.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.usecase.CreateTaskUseCase
import com.ubl.todolist.presentation.screens.home.TaskListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTaskViewModel(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListUiState(isLoading = false))
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    private val _saveSuccessEvent = MutableSharedFlow<Unit>()
    val saveSuccessEvent: SharedFlow<Unit> = _saveSuccessEvent.asSharedFlow()



    fun insertTask(task: Task) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                createTaskUseCase.execute(task)
                _saveSuccessEvent.emit(Unit)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(userMessage = e.message ?: "Failed to save task", isLoading = false)
                }
            }
        }
    }


}

