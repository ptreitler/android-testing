package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TaskDetailFragmentTest {
    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun resetRepository() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_displayedInUI() = runBlockingTest {
        // Given: Add active (incomplete) task to the DB
        val activeTask = Task("Active task", "An active test task", false)

        repository.saveTask(activeTask)

        // When: Details Fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
    }
}