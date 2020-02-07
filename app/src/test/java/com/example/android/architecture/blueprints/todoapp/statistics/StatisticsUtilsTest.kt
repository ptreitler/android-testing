@file:Suppress("IncorrectScope")

package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.closeTo
import org.junit.Assert.*
import org.junit.Test

@Suppress("IncorrectScope")
class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedTasksWithOnlyOneActiveTask() {
        val tasks = listOf(Task(
                "Active task",
                "An active task for the unit test",
                isCompleted = false
        ))
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertThat(activeTasksPercent, `is`(100f))
        assertThat(completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedTasksWithTwoOutOfThreeTasksCompleted() {
        val tasks = listOf(Task("A", "A", true),
                Task("B", "B", true),
                Task("C", "C", false)
        )
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertThat(activeTasksPercent.toDouble(), closeTo(33.33, 0.01))
        assertThat(completedTasksPercent.toDouble(), closeTo(66.66, 0.01))
    }

    @Test
    fun getActiveAndCompletedTasksWithEmptyList() {
        val tasks = emptyList<Task>()
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertThat(activeTasksPercent, `is`(0f))
        assertThat(completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedTasksWithNull() {
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(null)
        assertThat(activeTasksPercent, `is`(0f))
        assertThat(completedTasksPercent, `is`(0f))
    }
}