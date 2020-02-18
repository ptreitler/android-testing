package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TasksViewModelTest {

    private lateinit var fakeTestRepository: TasksRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        fakeTestRepository = FakeTestRepository().apply {
            addTasks(
                    Task("Task one", "Description one"),
                    Task("Task two", "Description two", true),
                    Task("Task three", "Description three", true)
            )
        }
        tasksViewModel = TasksViewModel(fakeTestRepository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh view model

        // when adding a new task
        tasksViewModel.addNewTask()

        // then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isNotNull()
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // Given a fresh ViewModel

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        val tasksAddViewVisible = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(tasksAddViewVisible).isTrue()
    }
}