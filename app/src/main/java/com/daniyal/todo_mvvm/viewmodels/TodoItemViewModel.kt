package com.daniyal.todo_mvvm.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.data.repo.TodoItemRepo
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.GeneralHelper
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class TodoItemViewModel @ViewModelInject constructor(private val todoItemRepo: TodoItemRepo) :
    ViewModel() {

    val itemState: MutableLiveData<ResponseEvent<List<TodoItemResponse>>> = MutableLiveData()

    init {
        getTodoItems()
    }

     fun getTodoItems() {
        itemState.value = ResponseEvent.Loading
        viewModelScope.launch {
            Dispatchers.IO
            try {
                val response = todoItemRepo.fetchItemsFromServer()
                if (response.isSuccessful) {
                    Constants.isItemUpdate = false
                    itemState.value = ResponseEvent.Success(response.body()?.filter { it.user_id == PrefsHelper.getString(Constants.userID) })
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message = GeneralHelper.parseFailureJson((jObjError))
                    itemState.value = ResponseEvent.Failure(message)
                }

            } catch (e: Exception) {
                itemState.value = ResponseEvent.Failure(e.message)
            }
        }
    }


    fun deleteTodoItem(id: Int?) {
        itemState.value = ResponseEvent.Loading
        viewModelScope.launch {
            Dispatchers.IO
            try {
                val response = todoItemRepo.deleteItemOnServer(id)
                if (response.isSuccessful) {
                    //itemState.value = ResponseEvent.Success(response.body())
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message = GeneralHelper.parseFailureJson((jObjError))
                    itemState.value = ResponseEvent.Failure(message)
                }

            } catch (e: Exception) {
                itemState.value = ResponseEvent.Failure(e.message)
            }
        }
    }



}