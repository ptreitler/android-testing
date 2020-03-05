package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class TasksLocalDataSourceTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksLocalDataSource: TasksLocalDataSource
    private lateinit var database: ToDoDatabase

    private val taskId = "1234"
    private lateinit var testTask: Task

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ToDoDatabase::class.java
                ).allowMainThreadQueries()
                .build()

        tasksLocalDataSource = TasksLocalDataSource(database.taskDao(), Dispatchers.Main)

        testTask = Task("name", "description", false, taskId)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    // runBlocking is used here because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    // TODO: Replace with runBlockingTest once issue is resolved
    @Test
    fun saveTask_retrievesTask() = runBlocking {
        // GIVEN - A new task saved in the database.
        tasksLocalDataSource.saveTask(testTask)

        // WHEN  - Task retrieved by ID.
        val result = tasksLocalDataSource.getTask(taskId)

        // THEN - Same task is returned.
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(testTask)
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runBlocking {
        // GIVEN - A new active task in the local data source.
        tasksLocalDataSource.saveTask(testTask)

        // WHEN - Task marked as complete.
        tasksLocalDataSource.completeTask(testTask)

        // THEN - The task can be retrieved from the local data source and is complete.
        val result = tasksLocalDataSource.getTask(taskId)
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.isCompleted).isTrue()
    }
}