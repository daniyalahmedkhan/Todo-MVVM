package com.daniyal.todo_mvvm.adapters.home

import androidx.annotation.NonNull
import java.util.*

class Event(@param:NonNull val title: String, @NonNull date: Date) {
    private val date: Date

    fun getDate(): Date {
        return date
    }

    init {
        this.date = date
    }
}