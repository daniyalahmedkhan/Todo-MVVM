package com.daniyal.todo_mvvm.ui

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.adapters.home.*
import com.daniyal.todo_mvvm.viewmodels.TodoItemViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_toolbar.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val todoItemViewModel: TodoItemViewModel by viewModels()
    private val items: MutableList<ListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoItemViewModel.itemState.observe(this, Observer {

        })

        val events: Map<Date, List<Event>> =
            toMap(loadEvents())

        for (date in events.keys) {
            val header = HeaderItem(date)
            items.add(header)
            for (event in events[date]!!) {
                val item = EventItem(event)
                items.add(item)
            }
        }

        //val recyclerView = findViewById<View>(R.id.lst_items) as RecyclerView
        RV_TodoItems.layoutManager = LinearLayoutManager(this)
        RV_TodoItems.adapter = EventAdapters(items)


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, R.string.app_name,
            R.string.app_name
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        IV_Menu.setOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }

    }

    @NonNull
    private fun loadEvents(): List<Event> {
        val events: MutableList<Event> = ArrayList()
        for (i in 1..49) {
            events.add(Event("event $i", buildRandomDateInCurrentMonth()!!))
        }
        return events
    }

    private fun buildRandomDateInCurrentMonth(): Date? {
        val random = Random()
        return com.daniyal.todo_mvvm.utilities.DateUtils.buildDate(random.nextInt(31) + 1)
    }

    @NonNull
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Galelry", Toast.LENGTH_SHORT).show()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}