package com.dodemy.roomcrudapp.di.modules

import com.dodemy.roomcrudapp.database.AppDatabase

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "task_database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2) // Add the migration here
            .build()
    }
}

