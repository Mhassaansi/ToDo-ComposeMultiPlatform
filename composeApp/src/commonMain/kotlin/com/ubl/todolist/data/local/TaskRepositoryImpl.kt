package com.ubl.todolist.data.local

import com.ubl.todolist.domain.repository.TaskRepository



class TaskRepositoryImpl(private val appDatabase: AppDatabase) :
    TaskRepository {
    override  fun getAllTasks() = appDatabase.taskDao().getAllTasks()
    override suspend fun getTaskById(taskId: Int): Task = appDatabase.taskDao().getTaskById(taskId)
    override suspend fun deleteTask(taskId: Int) = appDatabase.taskDao().deleteTask(taskId)
    override suspend fun saveTask(task: Task) = appDatabase.taskDao().insertTask(task)
    override suspend fun updateTask(task: Task) = appDatabase.taskDao().updateTask(task)
}