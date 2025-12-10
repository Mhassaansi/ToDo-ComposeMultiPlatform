package com.ubl.todolist.presentation.screens.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.usecase.CreateTaskUseCase
import com.ubl.todolist.domain.usecase.GetTaskByIdUseCase
import com.ubl.todolist.domain.usecase.UpdateTaskUseCase
import com.ubl.todolist.presentation.screens.taskDetail.TaskUiState
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
import kotlin.getValue

class AddTaskViewModel() : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _saveSuccessEvent = MutableSharedFlow<Unit>()
    val saveSuccessEvent: SharedFlow<Unit> = _saveSuccessEvent.asSharedFlow()

    private val getTaskByIdUseCase: GetTaskByIdUseCase by inject()
    private val createTaskUseCase: CreateTaskUseCase by inject()
    private val updateTaskUseCase: UpdateTaskUseCase by inject()


    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createTaskUseCase.execute(task)
                _saveSuccessEvent.emit(Unit)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(userMessage = e.message ?: "Failed to save task",)
                }
            }
        }
    }

    fun getTaskById(taskId: Int?) {
        if (taskId == null) return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val task: Task = getTaskByIdUseCase.execute(taskId)
                _uiState.value = _uiState.value.copy(task = task,
                    userMessage = "Task fetched successfully")

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(userMessage = e.message)
            }
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            try {
              updateTaskUseCase.execute(task)
                _uiState.value = _uiState.value.copy(userMessage = "Task updated successfully")
            }
            catch (e: Exception){
                _uiState.value = _uiState.value.copy(userMessage = e.message)
            }
    }
    }


}

