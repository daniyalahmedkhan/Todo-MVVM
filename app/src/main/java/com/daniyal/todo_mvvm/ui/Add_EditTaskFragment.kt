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
import com.daniyal.todo_mvvm.adapters.home.EventItem
import com.daniyal.todo_mvvm.adapters.home.ListItem
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.databinding.AddTaskFragmentBinding
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.GeneralHelper
import com.daniyal.todo_mvvm.viewmodels.PostTodoItemViewModel
import com.daniyal.todo_mvvm.viewmodels.TodoItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.other_toolbar.*
import java.util.*


@AndroidEntryPoint
class Add_EditTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var addTaskFragmentBinding: AddTaskFragmentBinding
    private val postTodoItemViewModel: PostTodoItemViewModel by viewModels()
    lateinit var dateTime: String

    companion object {
        private var listItem: ListItem? = null
        fun newInstance(listItem: ListItem?): Add_EditTaskFragment {
            this.listItem = listItem
            return Add_EditTaskFragment()
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

        if (listItem != null) {
            postTodoItemViewModel.isAddTask = false
            postTodoItemViewModel.taskId = (listItem as EventItem).taskid
            postTodoItemViewModel.title.value = (listItem as EventItem).event
            postTodoItemViewModel.desc.value = (listItem as EventItem).desc
            postTodoItemViewModel.category.value = (listItem as EventItem).category
         //   postTodoItemViewModel.dateTime.value = (listItem as EventItem).time
            postTodoItemViewModel.priority.value = (listItem as EventItem).priority.toInt()
        } else {
            postTodoItemViewModel.isAddTask = true
        }

        return addTaskFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setTitle("Add Task")
        toolbar.setNavigationOnClickListener {
            requireActivity().getSupportFragmentManager().popBackStack();
        }


        addTaskFragmentBinding.ETDateTime.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this, year, month, day)
            datePickerDialog.show()

        }

        postTodoItemViewModel.itemState.observe(requireActivity(), androidx.lifecycle.Observer {
            when (it) {

                is ResponseEvent.Loading -> {
                }
                is ResponseEvent.Failure -> {
                }
                is ResponseEvent.Success<TodoItemResponse> -> {
                    Constants.isItemUpdate = true
                    requireActivity().getSupportFragmentManager().popBackStack()
                }

            }
        })


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            requireActivity(), this, hour, minute,
            false
        )

        dateTime =
            "${GeneralHelper.dateTimeFormatter(dayOfMonth)}/${GeneralHelper.dateTimeFormatter(month + 1)}/${year}"
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        dateTime += " " + "${GeneralHelper.dateTimeFormatter(hourOfDay)}:${GeneralHelper.dateTimeFormatter(
            minute
        )}"
        addTaskFragmentBinding.ETDateTime.setText(dateTime)

    }

}