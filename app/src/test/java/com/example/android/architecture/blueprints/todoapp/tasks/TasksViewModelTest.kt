package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh view model
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // when adding a new task
        tasksViewModel.addNewTask()

        // then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isNotNull()
    }
}