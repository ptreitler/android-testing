package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedTasksWithOnlyOneActiveTask() {
        val tasks = listOf(Task(
                "Active task",
                "An active task for the unit test",
                isCompleted = false
        ))
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertEquals(100f, activeTasksPercent)
        assertEquals(0f, completedTasksPercent)
    }

    @Test
    fun getActiveAndCompletedTasksWithTwoOutOfThreeTasksCompleted() {
        val tasks = listOf(Task("A", "A", true),
                Task("B", "B", true),
                Task("C", "C", false)
        )
        val (activeTasksPercent, completedTasksPercent) = getActiveAndCompletedStats(tasks)
        assertEquals(33.33f, activeTasksPercent, 0.01f)
        assertEquals(66.66f, completedTasksPercent, 0.01f)
    }
}