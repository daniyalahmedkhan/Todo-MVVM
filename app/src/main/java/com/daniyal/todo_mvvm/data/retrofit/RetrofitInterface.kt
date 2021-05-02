package com.daniyal.todo_mvvm.data.retrofit

import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {

    @GET("todos")
    suspend fun getTodoItems(): Response<List<TodoItemResponse>>

    @POST("todos")
    suspend fun postTodoItem(@Body todoItemResponse: TodoItemResponse): Response<TodoItemResponse>

}