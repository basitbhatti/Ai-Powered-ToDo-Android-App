package com.basitbhatti.todoproject.data.di

import android.content.Context
import androidx.room.Room
import com.basitbhatti.todoproject.data.local.TaskDao
import com.basitbhatti.todoproject.data.local.TaskDatabase
import com.basitbhatti.todoproject.data.repository.TaskRepositoryImpl
import com.basitbhatti.todoproject.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDao(database: TaskDatabase): TaskDao{
        return database.getDao()
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(context: Context): TaskDatabase {
        return Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, "taskdb")
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }

}