package com.basitbhatti.todoproject.domain.use_cases

import com.basitbhatti.todoproject.domain.model.TaskItem
import com.basitbhatti.todoproject.domain.repository.TodoRepository
import kotlinx.coroutines.flow.StateFlow

class GetAllTasksUseCase(private val repository: TodoRepository){
    suspend operator fun invoke() : StateFlow<List<TaskItem>> = repository.getPrimaryTodoList()
}