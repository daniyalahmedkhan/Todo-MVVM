package com.daniyal.todo_mvvm.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.databinding.AddTaskFragmentBinding
import com.daniyal.todo_mvvm.viewmodels.PostTodoItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTaskFragment  : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var addTaskFragmentBinding: AddTaskFragmentBinding
    private val postTodoItemViewModel: PostTodoItemViewModel by viewModels()
    lateinit var dateTime: String


    companion object {

        fun newInstance(todoItemResponse: TodoItemResponse): EditTaskFragment {
            return EditTaskFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addTaskFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_task_fragment, container, false
        );
        addTaskFragmentBinding.viewmodel = postTodoItemViewModel

        return addTaskFragmentBinding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }

}