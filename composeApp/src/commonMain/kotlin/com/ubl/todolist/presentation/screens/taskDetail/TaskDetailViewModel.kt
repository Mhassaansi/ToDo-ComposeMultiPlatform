package com.ubl.todolist.presentation.screens.taskDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.usecase.DeleteTaskUseCase
import com.ubl.todolist.domain.usecase.GetTaskByIdUseCase
import com.ubl.todolist.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class TaskUiState(
    val task: Task ?= null,
    val userMessage: String? = null)
class TaskDetailViewModel : ViewModel(), KoinComponent {

    private val getTaskByIdUseCase: GetTaskByIdUseCase by inject()
    private val getTaskUseCase: UpdateTaskUseCase by inject()
    private val deleteTaskUseCase: DeleteTaskUseCase by inject()

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    fun getTaskById(taskId: Int?) {
        if (taskId == null) return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val task: Task = getTaskByIdUseCase.execute(taskId)
                _uiState.value = _uiState.value.copy(task = task)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(userMessage = e.message)
            }
        }
    }
    fun deleteTask(taskId: Int?) {
        if (taskId == null) return
       viewModelScope.launch(Dispatchers.IO){
           try {
               deleteTaskUseCase.execute(taskId)
               _uiState.value = _uiState.value.copy(userMessage = "Task deleted successfully")
           }
           catch (e: Exception){
               _uiState.value = _uiState.value.copy(userMessage = e.message)
           }
       }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getTaskUseCase.execute(task)
            }
            catch (e: Exception){
                _uiState.value = _uiState.value.copy(userMessage = e.message)
            }
        }
    }

}