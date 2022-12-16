package com.example.todolist

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

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (taskItem!= null){
            binding.tvTextTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskTextTitle.text = editable.newEditable(taskItem!!.name)
            binding.taskTextDesc.text = editable.newEditable(taskItem!!.desc)
        }else{
            binding.tvTextTitle.text = "New Task"
        }
        val activity = requireActivity()
        taskViewModel =  ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.addTaskBtn.setOnClickListener{
            addTask()
        }


    }

    private fun addTask() {
        val title = binding.taskTextTitle.text.toString()
        val desc = binding.taskTextDesc.text.toString()
        if (taskItem ==null){
            val newTask = TaskItem(title,desc,null,null)
            taskViewModel.addItem(newTask)
        }
        else{
            taskViewModel.updateItem(taskItem!!.id, title,desc,null)
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