package com.daniyal.todo_mvvm.data.repo

import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.data.retrofit.RetrofitInterface
import retrofit2.Response
import javax.inject.Inject

class TodoItemRepo @Inject constructor(private val baseApiInterface: RetrofitInterface) {

    suspend fun fetchItemsFromServer(): Response<List<TodoItemResponse>> {
        return baseApiInterface.getTodoItems()
    }

    suspend fun postItemToServer(todoItemResponse: TodoItemResponse): Response<TodoItemResponse> {
        return baseApiInterface.postTodoItem(todoItemResponse)
    }

    suspend fun editItemOnServer(id: Int? , todoItemResponse: TodoItemResponse): Response<TodoItemResponse> {
        return baseApiInterface.editTodoItem(id, todoItemResponse)
    }

    suspend fun deleteItemOnServer(id: Int? ): Response<TodoItemResponse> {
        return baseApiInterface.deleteTodoItem(id)
    }


}