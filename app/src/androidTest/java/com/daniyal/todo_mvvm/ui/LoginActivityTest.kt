package com.daniyal.todo_mvvm.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.daniyal.todo_mvvm.R

import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest{

    @get: Rule
    val activityRule: ActivityScenarioRule<LoginActivity> = ActivityScenarioRule(LoginActivity::class.java)


    /* Check Login Activity UI Elements, User Must be Logout */
    @Test
    fun checkHomeTitle_IsDisplayed(){
        onView(withId(R.id.IV_Logo)).check(matches(isDisplayed()))
    }


    /* Test user create/login functionality with firebase */
    /* - User must be logout
       - No Any prompt visible while test running like save password or third party app dialog
     */
    @Test
    fun checkLoginWithFirebaseEmailAndPass(){
        onView(withId(R.id.ET_User)).perform(typeText("Dani"), closeSoftKeyboard())
        onView(withId(R.id.ET_Pass)).perform(typeText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.BTN_Login)).perform(click())
        onView(isRoot()).perform(waitFor(10000))
        onView(withId(R.id.toolbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    /* Helping Func to Add Delay for Api Response*/
    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}