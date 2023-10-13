package com.example.calendar.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.calendar.domain.model.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task : Task)

    @Transaction
    @Query("SELECT * FROM task")
    suspend fun tasks(): List<Task>
}