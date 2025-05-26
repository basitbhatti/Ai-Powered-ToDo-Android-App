package com.basitbhatti.todoproject.presentation.viewmodel

import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeTaskViewModel : TaskViewModelContract {
    val sampleTasks = emptyList<TaskItemEntity>()
//        listOf(
//        TaskItemEntity(
//            id = 1,
//            title = "Task One",
//            description = "Description One",
//            583847L,
//            false,
//            1
//        ),
//        TaskItemEntity(
//            id = 2,
//            title = "Task Two",
//            description = "Description Two",
//            583847L,
//            false,
//            1
//        )
//    )


    private val _tasks = MutableStateFlow(sampleTasks)
    override val tasks: StateFlow<List<TaskItemEntity>> = _tasks
}
