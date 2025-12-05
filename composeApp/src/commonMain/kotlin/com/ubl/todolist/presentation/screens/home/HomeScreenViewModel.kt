package com.ubl.todolist.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.usecase.DeleteTaskUseCase
import com.ubl.todolist.domain.usecase.GetAllTaskUseCase
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




        fun deleteTask(taskId: Int) {
            _uiState.update { it.copy(isLoading = true, userMessage = null) }
            viewModelScope.launch {
                try {
                    deleteTaskUseCase.execute(taskId)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(userMessage = e.message ?: "")
                    }
                }
            }
        }
    }
}
