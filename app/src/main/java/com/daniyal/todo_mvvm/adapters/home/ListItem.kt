package com.daniyal.todo_mvvm.adapters.home

abstract class ListItem {
    abstract val type: Int

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_EVENT = 1
    }
}