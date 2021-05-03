package com.daniyal.todo_mvvm.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.adapters.home.*
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.databinding.HomeFragmentBinding
import com.daniyal.todo_mvvm.utilities.DateUtils
import com.daniyal.todo_mvvm.utilities.SwipeToDeleteCallback
import com.daniyal.todo_mvvm.viewmodels.TodoItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.toast
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val todoItemViewModel: TodoItemViewModel by viewModels()
    private val items: MutableList<ListItem> = ArrayList()
    private lateinit var homeItemsAdapter: HomeItemsAdapter


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        );
        binding.viewmodel = todoItemViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RVTodoItems.layoutManager = LinearLayoutManager(requireActivity())


        todoItemViewModel.itemState.observe(requireActivity(), androidx.lifecycle.Observer {

            when (it) {

                is ResponseEvent.Loading -> {
                }

                is ResponseEvent.Failure -> {
                }

                is ResponseEvent.Success<List<TodoItemResponse>> -> {

                    if (it.data!!.size > 0) {
                        val events: Map<Date, List<Event>> = toMap(loadEvents_(it.data))
                        items.clear()
                        binding.RVTodoItems.removeAllViews()
                        for (date in events.keys) {
                            val header = HeaderItem(date)
                            items.add(header)
                            for (event in events[date]!!) {
                                val item = EventItem(
                                    event.taskID,
                                    event.title,
                                    event.desc,
                                    event.category,
                                    event.priority,
                                    event.user_id,
                                    event.isCompleted,
                                    event.getDate().toString(),
                                    event.hour
                                )
                                items.add(item)

                            }
                        }


                    } else {
                        requireActivity().toast("No Item Found").setGravity(Gravity.CENTER, 0, 0)
                    }


                    homeItemsAdapter = HomeItemsAdapter(
                        items,
                        requireActivity()
                    ) { itemDto: ListItem, position: Int ->
                        openDetailFragment(
                            Add_EditTaskFragment.newInstance(
                                (itemDto as EventItem),
                                "Edit Task"
                            )
                        )

                    }
                    binding.RVTodoItems.adapter = homeItemsAdapter

                }

            }

        })

        /*
       * Search View in Toolbar to sort list items
       * */
        binding.itemSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                homeItemsAdapter.filter.filter(p0)
                return false
            }

        })

        enableSwipeToDeleteAndUndo()
    }

    private fun loadEvents_(list: List<TodoItemResponse>?): List<Event> {
        val events: MutableList<Event> = ArrayList()
        for (i in list!!.iterator()) {
            events.add(
                Event(
                    i.id,
                    i.title,
                    i.description,
                    i.category,
                    i.priority,
                    i.user_id,
                    i.isCompleted,
                    DateUtils.getDate(i.timestamp),
                    DateUtils.getHour(i.timestamp)

                )
            )
        }
        return events.reversed()
    }

    private fun toMap(@NonNull events: List<Event>): Map<Date, List<Event>> {
        val map: MutableMap<Date, MutableList<Event>> =
            TreeMap()
        for (event in events) {
            var value = map[event.getDate()]
            if (value == null) {
                value = ArrayList()
                map[event.getDate()] = value
            }
            value.add(event)
        }
        return map
    }

    /*
* Fragment Management
* */
    public fun openDetailFragment(fragment: Fragment) {

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.home_container, fragment)
            .addToBackStack(if (requireActivity().supportFragmentManager.backStackEntryCount == 0) fragment.tag else null)
            .commit()

    }

    private var item: ListItem? = null
    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(requireActivity()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition

                    item = homeItemsAdapter.items.get(position)
                    if (item?.type != 0) {
                        items.removeAt(position)
                        homeItemsAdapter.notifyDataSetChanged()
                        todoItemViewModel.deleteTodoItem((item as EventItem).taskid)
                        todoItemViewModel.getTodoItems()
                    } else {
                        homeItemsAdapter.notifyDataSetChanged()
                    }


                    // requireActivity().toast("${position}").show()
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.RVTodoItems)


    }

    override fun onPause() {
        super.onPause()
        (activity as HomeActivity?)?.hideToolbar(true)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity?)?.hideToolbar(false)
    }


}