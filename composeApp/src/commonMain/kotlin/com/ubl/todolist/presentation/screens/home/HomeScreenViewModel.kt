package com.ubl.todolist.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.usecase.DeleteTaskUseCase
import com.ubl.todolist.domain.usecase.GetAllTaskUseCase
import com.ubl.todolist.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)


class HomeScreenViewModel() : ViewModel() , KoinComponent {
    private val getAllTaskUseCase: GetAllTaskUseCase by inject()
    private val deleteTaskUseCase: DeleteTaskUseCase by inject()
    private val updateTaskUseCase: UpdateTaskUseCase by inject()

    private val _uiState = MutableStateFlow(TaskListUiState(isLoading = true))
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllTaskUseCase.execute().map { it }.collect { tasks ->
                    _uiState.update {
                        it.copy(tasks = tasks, isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        userMessage = "Error loading tasks: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun deleteTask(taskId: Int?) {
        if (taskId == null) return
        viewModelScope.launch(context = Dispatchers.IO){
            try {
                deleteTaskUseCase.execute(taskId)
                _uiState.update { currentState ->
                    currentState.copy(
                        tasks = currentState.tasks.filter { it.id != taskId },
                        userMessage = "Task deleted successfully"
                    )
                }
            }
            catch (e: Exception){
                _uiState.value = _uiState.value.copy(userMessage = e.message)
            }
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(context = Dispatchers.IO) {
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
