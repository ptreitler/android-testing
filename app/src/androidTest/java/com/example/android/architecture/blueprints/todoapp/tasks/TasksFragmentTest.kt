package com.example.android.architecture.blueprints.todoapp.tasks

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class TasksFragmentTest {
    private lateinit var repository: TasksRepository

    private lateinit var navController: NavController

    @Before
    fun initializeRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun resetRepository() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        // Given: On the tasks screen with two tasks
        val title1 = "Task 1"
        val id1 = "id1"
        repository.saveTask(Task(title1, "Test task 1", true, id1))
        repository.saveTask(Task("Task 2", "Test task 2", false, "id2"))
        initializeScenarioAndMockNavController()

        // When: Clicking the first task in the list
        onView(withId(R.id.tasks_list)).perform(
                actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(title1)), click())
        )

        // Then: Verify that we're navigating to the first detail screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(id1)
        )
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {
        // Given: On the tasks screen without any tasks
        initializeScenarioAndMockNavController()

        // When: Clicking the "+" FAB
        onView(withId(R.id.add_task_fab)).perform(click())

        // Then: Verify that we're navigating to the add task screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(null, "New Task")
        )
    }

    private fun initializeScenarioAndMockNavController() {
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }
}