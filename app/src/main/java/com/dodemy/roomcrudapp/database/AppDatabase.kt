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


import com.dodemy.roomcrudapp.utilities.Converters

@Database(entities = [Task::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
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
                )   .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Make MIGRATION_1_2 public
        @JvmField
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN start_date INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE tasks ADD COLUMN end_date INTEGER NOT NULL DEFAULT 0")
//                database.execSQL("ALTER TABLE tasks RENAME COLUMN startDate TO start_date")
//                database.execSQL("ALTER TABLE tasks RENAME COLUMN endDate TO end_date")
//                database.execSQL("ALTER TABLE tasks ADD COLUMN startDate INTEGER NOT NULL DEFAULT 0")
//                database.execSQL("ALTER TABLE tasks ADD COLUMN endDate INTEGER NOT NULL DEFAULT 0")
            }
        }

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Rename the existing columns
//                database.execSQL("ALTER TABLE tasks RENAME COLUMN startDate TO start_date")
//                database.execSQL("ALTER TABLE tasks RENAME COLUMN endDate TO end_date")
//            }
//        }

    }
}
