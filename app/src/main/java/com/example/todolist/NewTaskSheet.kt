package com.example.todolist

import android.app.TimePickerDialog
import android.os.Binder
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime
import java.sql.Time

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        if (taskItem != null) {
            binding.tvTextTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskTextTitle.text = editable.newEditable(taskItem!!.name)
            binding.taskTextDesc.text = editable.newEditable(taskItem!!.desc)

            if (taskItem!!.dueTime() != null) {
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }

        } else {
            binding.tvTextTitle.text = "New Task"
        }
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }
        binding.timePickerBtn.setOnClickListener {
            openTimePicker()
        }


    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due Date")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timePickerBtn.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    private fun addTask() {
        val title = binding.taskTextTitle.text.toString()
        val desc = binding.taskTextDesc.text.toString()
        val dueTimeString = if (dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        if (taskItem == null) {
            val newTask = TaskItem(title, desc, dueTimeString, null)
            taskViewModel.addItem(newTask)
        } else {
            taskItem!!.name = title
            taskItem!!.desc = desc
            taskItem!!.dueTimeString =  dueTimeString
            taskViewModel.updateItem(taskItem!!)
        }
        binding.taskTextTitle.setText("")
        binding.taskTextDesc.setText("")
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


}