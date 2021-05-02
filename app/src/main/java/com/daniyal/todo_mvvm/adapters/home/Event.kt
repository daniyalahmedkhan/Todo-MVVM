package com.daniyal.todo_mvvm.adapters.home

import androidx.annotation.NonNull
import java.util.*

class Event(val taskID: Int? , val title: String, val desc: String, val category: String , val priority: String ,
            val user_id: String, val isCompleted: Boolean, date: Date, val hour: String) {

    private val date: Date

    fun getDate(): Date {
        return date
    }

    init {
        this.date = date
    }
}