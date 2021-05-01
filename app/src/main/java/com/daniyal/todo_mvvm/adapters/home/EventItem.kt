package com.daniyal.todo_mvvm.adapters.home

import androidx.annotation.NonNull

class EventItem constructor(): ListItem() {
    @NonNull
    private var event: Event? = null

    constructor(@NonNull event: Event?) : this() {
        this.event = event
    }

    @NonNull
    fun getEvent(): Event? {
        return event
    }


    override val type: Int
        get() = TYPE_EVENT

}