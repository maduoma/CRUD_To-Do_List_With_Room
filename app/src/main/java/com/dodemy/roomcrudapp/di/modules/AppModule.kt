package com.dodemy.roomcrudapp.di.modules

import com.dodemy.roomcrudapp.data.dao.TaskDao
import com.dodemy.roomcrudapp.database.AppDatabase
import com.dodemy.roomcrudapp.repository.TaskRepository
import com.dodemy.roomcrudapp.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }
}
