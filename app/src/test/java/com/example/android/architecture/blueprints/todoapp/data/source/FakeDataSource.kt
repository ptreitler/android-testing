package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task

class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) : TasksDataSource {
    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.removeIf { it.id == taskId }
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasks(): Result<List<Task>> = tasks?.let { Result.Success(it) }
            ?: Result.Error(Exception("No tasks found"))

    override suspend fun refreshTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTask(taskId: String): Result<Task> = tasks?.firstOrNull { it.id == taskId }?.let {
        Result.Success(it)
    } ?: Result.Error(Exception("Task with ID $taskId not found"))

    override suspend fun refreshTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearCompletedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
