package com.basitbhatti.todoproject.data.repository

import com.basitbhatti.todoproject.data.local.TaskDao
import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import com.basitbhatti.todoproject.domain.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(val dao: TaskDao) : TaskRepository {

    override fun getPrimaryTodoList(): StateFlow<List<TaskItemEntity>> {
        return dao.getAllTasks()
    }

    override suspend fun addTask(taskItemEntity: TaskItemEntity) {
        dao.insertTask(taskItemEntity)
    }

    override suspend fun deleteTask(taskItemEntity: TaskItemEntity) {
        dao.deleteTask(taskItemEntity)
    }

    override suspend fun editTask(taskItemEntity: TaskItemEntity) {
        dao.updateTask(taskItemEntity)
    }
}

