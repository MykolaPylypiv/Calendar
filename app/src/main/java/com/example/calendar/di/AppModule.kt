package com.example.calendar.di

import android.content.Context
import androidx.room.Room
import com.example.calendar.app.Languages
import com.example.calendar.data.db.AppDatabase
import com.example.calendar.data.db.room.TasksDao
import com.example.calendar.data.repository.Repository
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
    private const val DATABASE_NAME = "database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

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

    @Provides
    fun provideLanguage(): Languages {
        return Languages.English()
    }

    @Provides
    @Singleton
    fun provideConst(): Repository {
        return Repository
    }
}