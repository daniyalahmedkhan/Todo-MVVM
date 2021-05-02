package com.daniyal.todo_mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniyal.todo_mvvm.R
import kotlinx.android.synthetic.main.other_toolbar.*

class AddTaskFragment : Fragment() {

    companion object {

        fun newInstance(): AddTaskFragment {
            return AddTaskFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setTitle("Add Task")
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
}