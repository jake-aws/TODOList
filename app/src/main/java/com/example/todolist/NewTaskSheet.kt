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

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (taskItem != null) {
            binding.tvTextTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskTextTitle.text = editable.newEditable(taskItem!!.name)
            binding.taskTextDesc.text = editable.newEditable(taskItem!!.desc)

            if (taskItem!!.dueTime != null) {
                dueTime = taskItem!!.dueTime!!
                updateTimeButtonText()
            }

        } else {
            binding.tvTextTitle.text = "New Task"
        }
        val activity = requireActivity()
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
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, false)
        dialog.setTitle("Task Due Date")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timePickerBtn.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    private fun addTask() {
        val title = binding.taskTextTitle.text.toString()
        val desc = binding.taskTextDesc.text.toString()
        if (taskItem == null) {
            val newTask = TaskItem(title, desc, dueTime, null)
            taskViewModel.addItem(newTask)
        } else {
            taskViewModel.updateItem(taskItem!!.id, title, desc, dueTime)
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