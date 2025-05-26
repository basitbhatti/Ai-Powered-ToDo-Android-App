package com.basitbhatti.todoproject.presentation.viewmodel

import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import kotlinx.coroutines.flow.StateFlow

interface TaskViewModelContract {
    val tasks: StateFlow<List<TaskItemEntity>>
}
