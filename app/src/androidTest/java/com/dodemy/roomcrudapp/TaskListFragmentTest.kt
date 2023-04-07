package com.dodemy.roomcrudapp

import org.hamcrest.Matchers.not
import androidx.test.espresso.Espresso.onView
import com.dodemy.roomcrudapp.ui.activities.MainActivity
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dodemy.roomcrudapp.ui.adapters.TaskListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertNewTask() {
        // Click on the "Add Task" FloatingActionButton
        onView(withId(R.id.fabAddTask)).perform(click())

        // Type the task title and description into EditTexts
        onView(withId(R.id.etTaskTitle)).perform(typeText("Test Task Title"))
        onView(withId(R.id.etTaskDescription)).perform(typeText("Test Task Description"))

        // Click on the "Save Task" Button
        onView(withId(R.id.btnSaveTask)).perform(click())

        // Check if the task is displayed in the RecyclerView
        onView(withId(R.id.tvTaskTitle)).check(matches(withText("Test Task Title")))
        onView(withId(R.id.tvTaskDescription)).check(matches(withText("Test Task Description")))
    }

    private fun insertTestTask() {
        // Click on the "Add Task" FloatingActionButton
        onView(withId(R.id.fabAddTask)).perform(click())

        // Type the task title and description into EditTexts
        onView(withId(R.id.etTaskTitle)).perform(typeText("Initial Task Title"))
        onView(withId(R.id.etTaskDescription)).perform(typeText("Initial Task Description"))

        // Click on the "Save Task" Button
        onView(withId(R.id.btnSaveTask)).perform(click())
    }
    @Test
    fun updateTask() {
        // Insert a test task
        insertTestTask()

        // Click on the task in the RecyclerView to open the TaskDetailsFragment
        onView(withId(R.id.rvTaskList)).perform(RecyclerViewActions.actionOnItemAtPosition<TaskListAdapter.TaskViewHolder>(0, click()))

        // Update the task title and description
        onView(withId(R.id.etTaskTitle)).perform(replaceText("Updated Task Title"))
        onView(withId(R.id.etTaskDescription)).perform(replaceText("Updated Task Description"))

        // Click on the "Save Task" Button
        onView(withId(R.id.btnSaveTask)).perform(click())

        // Check if the updated task is displayed in the RecyclerView
        onView(withId(R.id.tvTaskTitle)).check(matches(withText("Updated Task Title")))
        onView(withId(R.id.tvTaskDescription)).check(matches(withText("Updated Task Description")))
    }

    @Test
    fun deleteTask() {
        // Assuming there is already a task to be deleted

        // Click on the task item in the RecyclerView to open the TaskDetailsFragment
        onView(withId(R.id.rvTaskList))
            .perform(RecyclerViewActions.actionOnItemAtPosition<TaskListAdapter.TaskViewHolder>(0, click()))

        // Click on the "Delete Task" Button
        onView(withId(R.id.btnDeleteTask)).perform(click())

        // Confirm the deletion in the AlertDialog by clicking on the "Yes" button
        onView(withText(R.string.yes)).perform(click())

        // Check if the task has been removed from the RecyclerView
        onView(withId(R.id.rvTaskList))
            .check(matches(not(hasDescendant(withText("Task Title To Be Deleted")))))
    }

}
