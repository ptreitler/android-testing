@file:Suppress("IncorrectScope")

package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import com.google.common.truth.Truth.assertThat
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
        assertThat(activeTasksPercent).isEqualTo(100f)
        assertThat(completedTasksPercent).isEqualTo(0f)
    }

    @Test
    fun getActiveAndCompletedTasksWithTwoOutOfThreeTasksCompleted() {
        val tasks = listOf(Task("A", "A", true),
                Task("B", "B", true),
                Task("C", "C", false)
        )
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertThat(activeTasksPercent).isWithin(0.01f).of(33.33f)
        assertThat(completedTasksPercent).isWithin(0.01f).of(66.66f)
    }

    @Test
    fun getActiveAndCompletedTasksWithEmptyList() {
        val tasks = emptyList<Task>()
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertThat(activeTasksPercent).isEqualTo(0f)
        assertThat(completedTasksPercent).isEqualTo(0f)
    }

    @Test
    fun getActiveAndCompletedTasksWithNull() {
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(null)
        assertThat(activeTasksPercent).isEqualTo(0f)
        assertThat(completedTasksPercent).isEqualTo(0f)
    }
}