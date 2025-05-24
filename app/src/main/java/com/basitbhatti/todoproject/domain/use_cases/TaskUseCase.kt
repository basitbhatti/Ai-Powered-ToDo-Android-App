package com.basitbhatti.todoproject.domain.use_cases

import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import com.basitbhatti.todoproject.domain.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveAllTasksUseCase @Inject constructor(private val repository: TaskRepository){
    operator fun invoke() : StateFlow<List<TaskItemEntity>> = repository.getPrimaryTodoList()
}
class AddTaskUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(taskItemEntity: TaskItemEntity) = repository.addTask(taskItemEntity)
}
class DeleteTaskUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(taskItemEntity: TaskItemEntity) = repository.deleteTask(taskItemEntity)
}
class EditTaskUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(taskItemEntity: TaskItemEntity) = repository.editTask(taskItemEntity)
}