package com.daniyal.todo_mvvm.utilities


object Constants {

    val keys = System.loadLibrary("keys")
    external fun getBaseUAT(): String?
    val BASE_URL: String = String(android.util.Base64.decode(getBaseUAT(), android.util.Base64.DEFAULT)) //..UAT

    val isLogin: String = "isLogin"
    val userID: String = "userID"
    val userImage: String = "userImage"
}