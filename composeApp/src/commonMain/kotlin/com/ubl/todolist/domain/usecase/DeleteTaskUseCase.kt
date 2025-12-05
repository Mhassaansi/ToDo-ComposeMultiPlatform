package com.ubl.todolist.domain.usecase

import com.ubl.todolist.data.local.Task
import com.ubl.todolist.domain.repository.TaskRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DeleteTaskUseCase : KoinComponent {
    private val taskRepository: TaskRepository by inject()
    suspend  fun  execute(taskId: Int) = taskRepository.deleteTask(taskId = taskId)
}