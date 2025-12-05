package com.ubl.todolist.domain.usecase

import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.repository.TaskRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class GetTaskByIdUseCase : KoinComponent {
    private val taskRepository: TaskRepository by inject()
    suspend fun execute(taskId: Int) = taskRepository.getTaskById(taskId = taskId)
}