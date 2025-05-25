package com.basitbhatti.todoproject.data.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.basitbhatti.todoproject.data.local.TaskDao
import com.basitbhatti.todoproject.data.local.TaskDatabase
import com.basitbhatti.todoproject.data.repository.TaskRepositoryImpl
import com.basitbhatti.todoproject.domain.repository.TaskRepository
import com.basitbhatti.todoproject.domain.use_cases.AddTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.DeleteTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.EditTaskUseCase
import com.basitbhatti.todoproject.domain.use_cases.ObserveAllTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDao(database: TaskDatabase): TaskDao {
        return database.getDao()
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "taskdb")
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetTasksUseCase(repository: TaskRepository): ObserveAllTasksUseCase{
        return ObserveAllTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(repository: TaskRepository): AddTaskUseCase{
        return AddTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase{
        return DeleteTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(repository: TaskRepository): EditTaskUseCase{
        return EditTaskUseCase(repository)
    }

}