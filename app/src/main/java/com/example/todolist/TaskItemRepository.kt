package com.example.todolist

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskItemRepository(private val taskItemDAO: TaskItemDAO) {

    val allTaskItems: Flow<List<TaskItem>> = taskItemDAO.allTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem) {
        taskItemDAO.insertTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem) {
        taskItemDAO.updateTaskItem(taskItem)
    }

    suspend fun deleteTaskItem(taskItem: TaskItem) {
        taskItemDAO.deleteTaskItem(taskItem)
    }
}