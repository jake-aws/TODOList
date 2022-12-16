package com.example.todolist

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
) {

    fun isCompleted() = completedDate != null
    fun imgResource(): Int = if (isCompleted()) R.drawable.ic_baseline_check_circle_outline_24 else
        R.drawable.ic_baseline_radio_button_unchecked_24

    fun imgColor(context: Context): Int = if (isCompleted()) purple(context) else black(context)
    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}