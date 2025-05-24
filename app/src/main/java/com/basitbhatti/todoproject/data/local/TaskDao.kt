package com.basitbhatti.todoproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TaskItemEntity>>

    @Insert()
    suspend fun insertTask(taskItemEntity: TaskItemEntity)

    @Update()
    suspend fun updateTask(taskItemEntity: TaskItemEntity)

    @Delete()
    suspend fun deleteTask(taskItemEntity: TaskItemEntity)


}