package com.dodemy.roomcrudapp.database

import com.dodemy.roomcrudapp.data.dao.TaskDao
import com.dodemy.roomcrudapp.data.entities.Task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


import com.dodemy.roomcrudapp.database.converters.DateConverter

@Database(entities = [Task::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )   .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Add the new migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
        @JvmField
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN reminder_frequency TEXT NOT NULL DEFAULT 'NONE'")
            }
        }
        // Make MIGRATION_1_2 public
        @JvmField
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN start_date INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE tasks ADD COLUMN end_date INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
