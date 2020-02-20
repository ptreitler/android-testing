package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase_Impl
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

object ServiceLocator {
    private var database: ToDoDatabase? = null

    @Volatile
    var tasksRepository: TasksRepository? = null

    fun provideTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): TasksRepository = DefaultTasksRepository(
            TasksRemoteDataSource,
            createTasksLocalDataSource(context)
    ).also {
        tasksRepository = it
    }

    private fun createTasksLocalDataSource(context: Context): TasksDataSource {
        val database = database ?: createDatabase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDatabase(context: Context): ToDoDatabase =
            Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "Tasks.db"
            ).build().also{ database = it }
}