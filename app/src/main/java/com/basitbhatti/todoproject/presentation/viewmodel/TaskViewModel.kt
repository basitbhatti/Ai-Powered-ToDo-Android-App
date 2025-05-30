package com.basitbhatti.todoproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import com.basitbhatti.todoproject.domain.use_cases.AddTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.DeleteTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.EditTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.ObserveActiveTasksUseCase
import com.basitbhatti.todoproject.domain.use_cases.ObserveAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasksUseCase: ObserveAllTasksUseCase,
    private val getActiveTasksUseCase: ObserveActiveTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskRepository: DeleteTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
) : ViewModel(){

    private var _tasks = MutableStateFlow<List<TaskItemEntity>>(emptyList())
    val tasks = _tasks.asStateFlow()


    private var _activeTasks = MutableStateFlow<List<TaskItemEntity>>(emptyList())
    val activeTasks = _activeTasks.asStateFlow()


    init {
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            getActiveTasksUseCase().collect{
                _activeTasks.value = it
            }
            getAllTasksUseCase().collect {
                _tasks.value = it
                it.forEach {
                    Log.d("TAGTASK", "fetching : ${it.title}")
                }
            }
        }
    }

    fun addTask(task: TaskItemEntity) {
        Log.d("TAGTASK", "addTask: ")
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