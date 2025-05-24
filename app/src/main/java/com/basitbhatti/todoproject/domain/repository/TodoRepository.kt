package com.basitbhatti.todoproject.domain.repository

import com.basitbhatti.todoproject.domain.model.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow

interface TodoRepository {
    suspend fun getPrimaryTodoList(): MutableStateFlow<List<TaskItem>>
    suspend fun addTask(taskItem: TaskItem)
    suspend fun deleteTask(taskItem: TaskItem)
    suspend fun editTask(taskItem: TaskItem)
}