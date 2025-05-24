package com.basitbhatti.todoproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("task")
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDateTime: Long,
    val isCompleted: Boolean,
    val priority: Int
)