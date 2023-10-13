package com.example.calendar.di

import android.content.Context
import androidx.room.Room
import com.example.calendar.app.DATABASE_NAME
import com.example.calendar.data.db.AppDatabase
import com.example.calendar.data.db.room.TasksDao
import com.example.calendar.data.repository.TaskRepository
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "room_database").build()

    @Provides
    @Singleton
    fun provideChannelDao(db: AppDatabase): TasksDao {
        return db.tasksDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TasksDao): TaskRepository {
        return TaskRepository.Base(dao)
    }
}