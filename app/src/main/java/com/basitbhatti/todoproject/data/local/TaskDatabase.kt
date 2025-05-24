package com.basitbhatti.todoproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.basitbhatti.todoproject.domain.model.TaskItemEntity

@Database(entities = [TaskItemEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun getDao(): TaskDao
}