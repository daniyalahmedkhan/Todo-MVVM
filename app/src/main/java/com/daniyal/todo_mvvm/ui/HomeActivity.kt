package com.daniyal.todo_mvvm.ui

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.databinding.ActivityMainBinding
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


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
            openTaskFragment()
        } else if (id == R.id.logout) {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            PrefsHelper.putBoolean(Constants.isLogin, false)
            startActivity(intentFor<LoginActivity>().newTask())
            finish()
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

    private fun openTaskFragment() {
        val fm: FragmentManager = supportFragmentManager
        val fragment: HomeFragment = fm.findFragmentById(R.id.home_container) as HomeFragment
        fragment.openDetailFragment(Add_EditTaskFragment.newInstance(null, "Add Task"))
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

        IV_AddTask.setOnClickListener {
            openTaskFragment()
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

    fun hideToolbar(boolean: Boolean) {
        toolbar.visibility = if (boolean) View.GONE else View.VISIBLE
    }
}