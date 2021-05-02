package com.daniyal.todo_mvvm.adapters.home

import androidx.annotation.NonNull

data class EventItem constructor(val taskid: Int? ,val event: String?, val desc: String, val category: String, val priority: String,
                                 val user_id: String, val isCompleted: Boolean , val time: String, val hour: String) : ListItem() {

//    private var event: String? = null
//    private var desc: String? = null
//    private var category: String? = null
//    private var priority: String? = null
//    private var user_id: String? = null
//    private var isCompleted: Boolean? = null


//    constructor(
//        event: String?, desc: String, category: String, priority: String,
//        user_id: String, isCompleted: Boolean
//    ) : this() {
//        this.event = event
//        this.desc = desc
//        this.category = category
//        this.priority = priority
//        this.user_id = user_id
//        this.isCompleted = isCompleted
//
//    }


//    fun getEvent(): String? {
//        return event
//    }

    override val type: Int
        get() = TYPE_EVENT

}