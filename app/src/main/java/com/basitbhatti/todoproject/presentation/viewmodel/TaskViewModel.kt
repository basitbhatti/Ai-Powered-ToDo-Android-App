package com.basitbhatti.todoproject.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import com.basitbhatti.todoproject.domain.use_cases.AddTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.DeleteTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.EditTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.ObserveActiveTasksUseCase
import com.basitbhatti.todoproject.domain.use_cases.ObserveAllTasksUseCase
import com.basitbhatti.todoproject.utils.TASK_ID
import com.basitbhatti.todoproject.utils.TASK_TITLE
import com.basitbhatti.todoproject.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getAllTasksUseCase: ObserveAllTasksUseCase,
    private val getActiveTasksUseCase: ObserveActiveTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskRepository: DeleteTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
) : ViewModel() {

    private var _tasks = MutableStateFlow<List<TaskItemEntity>>(emptyList())
    val tasks = _tasks.asStateFlow()


    private var _activeTasks = MutableStateFlow<List<TaskItemEntity>>(emptyList())
    val activeTasks = _activeTasks.asStateFlow()


    init {
        fetchAllTasks()
    }

    fun sendTopPriorityReminder() {
        val topPriorityTask = _activeTasks?.value?.maxByOrNull { it.priority }
        if (topPriorityTask != null) {

            Log.d("TAGSCHEDULE", "sendTopPriorityReminder Priority: ${topPriorityTask.priority}")

            val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
                30, TimeUnit.MINUTES
            ).addTag("${topPriorityTask.id}")

            workRequest.setInputData(
                workDataOf(
                    TASK_ID to topPriorityTask.id,
                    TASK_TITLE to topPriorityTask.title
                )
            )

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "top_priority_reminder",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest.build()
            )
        }
    }

    fun fetchAllTasks() {

        viewModelScope.launch(Dispatchers.IO) {
            getActiveTasksUseCase().collect {
                _activeTasks.value = it
                _activeTasks.value.forEach {
                    Log.d("TAGSCHEDULE", "active : ${it.title}")
                }

            }
            getAllTasksUseCase().collect {
                _tasks.value = it
                it.forEach {
                }
            }
        }
    }

    fun addTask(task: TaskItemEntity) {
        Log.d("TAGSCHEDULE", "adding : ${task.title}")
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(task)
            fetchAllTasks()
        }
    }

    fun updateTask(task: TaskItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            editTaskUseCase(task)
            fetchAllTasks()
        }
    }

    fun deleteTask(task: TaskItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskRepository(task)
            fetchAllTasks()

        }
    }

}