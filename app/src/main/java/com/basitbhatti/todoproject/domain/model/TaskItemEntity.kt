package com.basitbhatti.todoproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("task")
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?=null,
    val dueDay: LocalDate,
    val isCompleted: Boolean,
    val priority: Int
)