package com.basitbhatti.todoproject.domain.model

import java.sql.Time
import java.util.Date

class TodoPrimaryItem (
    val title : String,
    val description: String,
    val dueDate: Date,
    val dueTime: Time,
    val isCompleted : Boolean
)