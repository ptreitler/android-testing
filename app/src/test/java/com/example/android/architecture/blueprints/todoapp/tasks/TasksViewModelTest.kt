package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TasksViewModelTest {
    private lateinit var fakeTestRepository: FakeTestRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

    @Test
    fun completeTask_dataAndSnackbarUpdated() {
        // Create an active task and add it to the repository.
        val task = Task("Title", "Description")
        fakeTestRepository.addTasks(task)

        // Mark the task as complete task.
        tasksViewModel.completeTask(task, true)

        // Verify the task is completed.
        assertThat(fakeTestRepository.tasksServiceData[task.id]?.isCompleted).isTrue()

        // Assert that the snackbar has been updated with the correct text.
        val snackbarText: Event<Int> = tasksViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled()).isEqualTo(R.string.task_marked_complete)
    }
}