package com.example.android.architecture.blueprints.todoapp

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.util.DataBindingIdlingResource
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

abstract class BaseEndToEndTest {

    protected lateinit var repository: TasksRepository

    @Before
    fun init() {
        repository = ServiceLocator.provideTasksRepository(ApplicationProvider.getApplicationContext())
        runBlocking {
            repository.deleteAllTasks()
        }
    }

    protected val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(
                EspressoIdlingResource.countingIdlingResource,
                dataBindingIdlingResource
        )
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(
                EspressoIdlingResource.countingIdlingResource,
                dataBindingIdlingResource
        )
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }
}