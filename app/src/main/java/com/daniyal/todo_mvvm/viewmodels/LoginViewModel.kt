package com.daniyal.todo_mvvm.viewmodels

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import dagger.hilt.android.qualifiers.ApplicationContext
import org.jetbrains.anko.toast


class LoginViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context
) :
    ViewModel() {

    val itemState: MutableLiveData<ResponseEvent<String>> = MutableLiveData()
    var username: String? = null
    var password: String? = null
    private lateinit var auth: FirebaseAuth


    fun validateFields(view: View) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            context.toast("Please fill all the values").setGravity(Gravity.CENTER, 0, 0)
        } else {
            checkUser(username.toString() + "@test.com", password.toString())
        }
    }

    private fun checkUser(email: String, password: String) {
        itemState.value = ResponseEvent.Loading
        auth = FirebaseAuth.getInstance()
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener(object : OnCompleteListener<SignInMethodQueryResult?> {
                override
                fun onComplete(task: Task<SignInMethodQueryResult?>) {
                    val isNewUser: Boolean = task.getResult()!!.getSignInMethods().isEmpty()
                    if (isNewUser) {
                        createUser(email, password)
                    } else {
                        signIn(email, password)
                    }
                }
            })
    }

    private fun createUser(email: String, password: String) {
        //   auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                signIn(email, password)
            } else {
                itemState.value = ResponseEvent.Failure("400")
                context.toast("Authentication Failed").setGravity(Gravity.CENTER, 0, 0)
            }
        }

    }

    private fun signIn(email: String, password: String) {
        //auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                PrefsHelper.putBoolean(Constants.isLogin, true)
                PrefsHelper.putString(Constants.userID, user.uid)
                itemState.value = ResponseEvent.Success("200")
            } else {
                itemState.value = ResponseEvent.Failure("400")
                context.toast("Authentication Failed").setGravity(Gravity.CENTER, 0, 0)
            }
        }
    }

}
