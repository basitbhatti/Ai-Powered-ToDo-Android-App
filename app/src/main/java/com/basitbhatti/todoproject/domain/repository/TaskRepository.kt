package com.basitbhatti.todoproject.domain.repository

import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getActiveTodoList(): Flow<List<TaskItemEntity>>
    fun getPrimaryTodoList(): Flow<List<TaskItemEntity>>
    suspend fun addTask(taskItemEntity: TaskItemEntity)
    suspend fun deleteTask(taskItemEntity: TaskItemEntity)
    suspend fun editTask(taskItemEntity: TaskItemEntity)
}