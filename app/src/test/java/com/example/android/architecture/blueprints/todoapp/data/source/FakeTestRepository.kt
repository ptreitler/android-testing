package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

class FakeTestRepository : TasksRepository {
    var tasksServiceData = LinkedHashMap<String, Task>()
    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> =
            Result.Success(tasksServiceData.values.toList())

    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    override suspend fun refreshTask(taskId: String) = TODO()

    override fun observeTask(taskId: String): LiveData<Result<Task>> = TODO()

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> =
            tasksServiceData[taskId]?.let { Result.Success(it) }
                    ?: Result.Error(Exception("Task $taskId not found!"))

    override suspend fun saveTask(task: Task) {
        addTasks(task)
    }

    override suspend fun completeTask(task: Task) {
        task.isCompleted = true
    }

    override suspend fun completeTask(taskId: String) {
        tasksServiceData[taskId]?.isCompleted = true
    }

    override suspend fun activateTask(task: Task) {
        task.isCompleted = false
    }

    override suspend fun activateTask(taskId: String) {
        tasksServiceData[taskId]?.isCompleted = false
    }

    override suspend fun clearCompletedTasks() {
        tasksServiceData.keys.filter { tasksServiceData[it]?.isCompleted == true }.forEach {
            tasksServiceData.remove(it)
        }
    }

    override suspend fun deleteAllTasks() {
        tasksServiceData.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
    }

    private fun addTasks(vararg tasks: Task) {
        tasks.forEach { tasksServiceData[it.id] = it }
        runBlocking { refreshTasks() }
    }
}