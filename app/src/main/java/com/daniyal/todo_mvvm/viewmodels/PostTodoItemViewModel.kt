package com.daniyal.todo_mvvm.viewmodels

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniyal.todo_mvvm.adapters.home.EventItem
import com.daniyal.todo_mvvm.adapters.home.ListItem
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.data.model.response.TodoItemResponse
import com.daniyal.todo_mvvm.data.repo.TodoItemRepo
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.GeneralHelper
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.lang.Exception

class PostTodoItemViewModel @ViewModelInject constructor(
    private val todoItemRepo: TodoItemRepo,
    @ApplicationContext val context: Context
) :
    ViewModel() {

    val itemState: MutableLiveData<ResponseEvent<TodoItemResponse>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()
    var desc: MutableLiveData<String> = MutableLiveData()
    var category: MutableLiveData<String> = MutableLiveData()
    var dateTime: MutableLiveData<String> = MutableLiveData()
    var priority: MutableLiveData<Int> = MutableLiveData()
    var isAddTask: Boolean = true
    var taskId: Int? = null

    fun getPriority(view: View, value: Int) {
        priority.value = value
    }

    fun validateFields(view: View) {
        if (title.value.isNullOrEmpty() || desc.value.isNullOrEmpty() || category.value.isNullOrEmpty() ||
            dateTime.value.isNullOrEmpty() || priority == null
        ) {
            context.toast("Please fill all the values").setGravity(Gravity.CENTER, 0, 0)
        } else {

            val todoItemResponse = TodoItemResponse(
                if (isAddTask) null else taskId , title.value.toString(), desc.value.toString(), category.value.toString(),
                GeneralHelper.convertDateIntoTimeStamp(dateTime.value.toString()),
                priority.value.toString(), PrefsHelper.getString(Constants.userID), false
            )

            if (isAddTask) postTodoItem(todoItemResponse) else editTodoItem(taskId , todoItemResponse)
        }
    }

    private fun postTodoItem(todoItemResponse: TodoItemResponse) {
        itemState.value = ResponseEvent.Loading
        viewModelScope.launch {
            Dispatchers.IO
            try {
                val response = todoItemRepo.postItemToServer(todoItemResponse)
                if (response.isSuccessful) {
                    itemState.value = ResponseEvent.Success(response.body())
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

    private fun editTodoItem(id: Int? , todoItemResponse: TodoItemResponse) {
        itemState.value = ResponseEvent.Loading
        viewModelScope.launch {
            Dispatchers.IO
            try {
                val response = todoItemRepo.editItemOnServer(id , todoItemResponse)
                if (response.isSuccessful) {
                    itemState.value = ResponseEvent.Success(response.body())
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