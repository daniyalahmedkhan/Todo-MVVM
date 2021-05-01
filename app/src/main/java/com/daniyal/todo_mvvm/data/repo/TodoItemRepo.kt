package com.daniyal.todo_mvvm.data.repo

import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.data.retrofit.RetrofitInterface
import retrofit2.Response
import javax.inject.Inject

class TodoItemRepo @Inject constructor(private val baseApiInterface: RetrofitInterface) {

    suspend fun fetchItemsFromServer(): Response<List<TodoItemResponse>> {
        return baseApiInterface.getTodoItems()
    }
}