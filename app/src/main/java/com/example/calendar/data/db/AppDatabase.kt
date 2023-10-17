package com.example.calendar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendar.data.db.room.TasksDao
import com.example.calendar.domain.model.Task

@Database(entities = [Task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}