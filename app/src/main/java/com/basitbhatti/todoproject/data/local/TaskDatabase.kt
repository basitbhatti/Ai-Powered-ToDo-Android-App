package com.basitbhatti.todoproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.basitbhatti.todoproject.domain.model.TaskItemEntity

@Database(entities = [TaskItemEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao
}