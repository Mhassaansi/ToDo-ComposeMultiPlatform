package com.ubl.todolist.domain.repository

import com.ubl.todolist.data.local.Task
import kotlinx.coroutines.flow.Flow


interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(taskId: Int): Task?
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(taskId: Int)
    suspend fun updateTask(task: Task)
}