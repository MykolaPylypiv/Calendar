package com.example.calendar.data.repository

import com.example.calendar.data.db.room.TasksDao
import com.example.calendar.domain.model.Task
import javax.inject.Inject

interface TaskRepository {

    suspend fun tasks(date: String): List<Task>

    suspend fun insert(task: Task)

    suspend fun delete(task: Task)

    suspend fun update(task: Task)

    class Base @Inject constructor(private val dao: TasksDao) : TaskRepository {

        override suspend fun tasks(date: String): List<Task> =
            dao.tasks(date = date, repeat = "One time")

        override suspend fun insert(task: Task) = dao.insert(task)

        override suspend fun delete(task: Task) = dao.delete(task)

        override suspend fun update(task: Task) = dao.update(task)
    }
}