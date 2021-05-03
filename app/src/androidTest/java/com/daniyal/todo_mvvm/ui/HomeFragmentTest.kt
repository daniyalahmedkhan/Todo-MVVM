package com.daniyal.todo_mvvm.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.daniyal.todo_mvvm.R
import kotlinx.android.synthetic.main.home_toolbar.*
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {

    @get: Rule
    val activityRule: ActivityScenarioRule<HomeActivity> =
        ActivityScenarioRule(HomeActivity::class.java)


    /* Check Items list available after 7s to fetch API data*/
    @Test
    fun checkHomeTitle_IsDisplayed() {
        Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(7000))
        Espresso.onView(ViewMatchers.withId(R.id.RV_TodoItems))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /* Check RecyclerView On Item Click Listener Open the Edit Fragment by clicking item at 1st position */
    @Test
    fun navEditFragment_from_HomeFragment_Test(){

        onView(isRoot()).perform(waitFor(7000))

        onView(withId(R.id.RV_TodoItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,
                click()))

        onView(withId(R.id.ET_Title)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    /* User Logout test */
    /* - User must be logged in  */
    @Test
    fun userLogout_ByNavigationDrawerItem(){
        onView(withId(R.id.IV_Menu)).perform(click())
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.logout));
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.IV_Logo)).check(matches(isDisplayed()))
    }


    /* Helping Func to Add Delay for Api Response*/
    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }


}