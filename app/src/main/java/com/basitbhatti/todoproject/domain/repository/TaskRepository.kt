package com.basitbhatti.todoproject.domain.repository

import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    fun getPrimaryTodoList(): StateFlow<List<TaskItemEntity>>
    suspend fun addTask(taskItemEntity: TaskItemEntity)
    suspend fun deleteTask(taskItemEntity: TaskItemEntity)
    suspend fun editTask(taskItemEntity: TaskItemEntity)
}