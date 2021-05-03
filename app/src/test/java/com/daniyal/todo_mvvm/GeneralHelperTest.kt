package com.daniyal.todo_mvvm

import com.daniyal.todo_mvvm.utilities.DateUtils
import com.daniyal.todo_mvvm.utilities.GeneralHelper
import junit.framework.Assert.assertTrue
import org.junit.Test

class GeneralHelperTest {

    /* Check Date With Expected Format From API */
    @Test
    fun `Date Parse Expected Format Should True _ PASS`(){
        assertTrue(GeneralHelper.isDateParse("2019-02-24 04:04:17.566515"))
    }

    @Test
    fun `Check Priority Label By Passing Value _ PASS`(){
        assertTrue(GeneralHelper.returnPriorityTag(0).equals("Urgent"))
    }

    @Test
    fun `Check Hour Extract from TimeStamp _ PASS`(){
        assertTrue(DateUtils.getHour(1620034447932).equals("02:34 PM"))
    }



}