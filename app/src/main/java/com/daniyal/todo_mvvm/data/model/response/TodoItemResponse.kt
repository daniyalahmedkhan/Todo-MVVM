package com.daniyal.todo_mvvm.data.model.response

import com.google.gson.annotations.SerializedName

data class TodoItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("priority") val priority: String,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)