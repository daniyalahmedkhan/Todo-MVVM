package com.daniyal.todo_mvvm.adapters.home

import androidx.annotation.NonNull
import java.util.*

class HeaderItem constructor(): ListItem() {
    @NonNull
    private var date: Date? = null

   constructor(@NonNull date: Date?) : this() {
        this.date = date
    }

    @NonNull
    fun getDate(): Date? {
        return date
    }


    override val type: Int
        get() = TYPE_HEADER

}