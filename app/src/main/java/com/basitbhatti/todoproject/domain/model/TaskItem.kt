package com.basitbhatti.todoproject.domain.model

data class TaskItem(
    val title: String,
    val description: String,
    val dueDateTime: Long,
    val isCompleted: Boolean
)