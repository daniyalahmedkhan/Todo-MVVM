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
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.adapters.home.*
import com.daniyal.todo_mvvm.data.enums.Pri
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.databinding.ActivityMainBinding
import com.daniyal.todo_mvvm.utilities.Constants.isItemUpdate
import com.daniyal.todo_mvvm.utilities.DateUtils
import com.daniyal.todo_mvvm.viewmodels.PostTodoItemViewModel
import com.daniyal.todo_mvvm.viewmodels.TodoItemViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_toolbar.*
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "FIRST"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
       *  Data Binding
       * */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerSetup()

        openDetailFragment(HomeFragment.newInstance())

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.new_task) {
            openDetailFragment(Add_EditTaskFragment.newInstance(null))
        } else if (id == R.id.logout) {
            Toast.makeText(this, "Galelry", Toast.LENGTH_SHORT).show()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    /*
* Fragment Management
* */
    private fun openDetailFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.home_container, fragment)
            .commit()
    }

    private fun drawerSetup() {
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

    override fun onPause() {
        super.onPause()
        println("Activity Pause ##################################")
    }

    override fun onResume() {
        super.onResume()
        println("Activity Resume ##################################")
    }


}