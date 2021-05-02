package com.daniyal.todo_mvvm.data.retrofit

import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @GET("todos")
    suspend fun getTodoItems(): Response<List<TodoItemResponse>>

    @POST("todos")
    suspend fun postTodoItem(@Body todoItemResponse: TodoItemResponse): Response<TodoItemResponse>

    @PUT("todos/{id}")
    suspend fun editTodoItem(@Path("id") id: Int?, @Body todoItemResponse: TodoItemResponse): Response<TodoItemResponse>
}