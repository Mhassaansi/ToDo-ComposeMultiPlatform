package com.ubl.todolist.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskData(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: String = "",
    val createDate: String = "",
    val updatedDate: String = "")
