package com.daniyal.todo_mvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.daniyal.todo_mvvm.R
import com.daniyal.todo_mvvm.data.model.remote.ResponseEvent
import com.daniyal.todo_mvvm.databinding.ActivityLoginBinding
import com.daniyal.todo_mvvm.utilities.Constants
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import com.daniyal.todo_mvvm.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Response
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private var firebaseAuth = FirebaseAuth.getInstance()
    private val Req_Code: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = loginViewModel

        // Configure Google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.IVGoogle.setOnClickListener {
            signInGoogle()
        }

        if (PrefsHelper.getBoolean(Constants.isLogin)) {
            startActivity(intentFor<HomeActivity>().newTask())
            finish()
        }

        loginViewModel.itemState.observe(this, Observer {

            when (it) {
                is ResponseEvent.Loading -> {
                }
                is ResponseEvent.Failure -> {
                }
                is ResponseEvent.Success<String> -> {
                    if (it.data.equals("200")) {
                        startActivity(intentFor<HomeActivity>().newTask())
                        finish()
                    }
                }
            }
        })
    }

    private fun signInGoogle() {

        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {

                PrefsHelper.putBoolean(Constants.isLogin, true)
                PrefsHelper.putString(Constants.userID, account.id)
                PrefsHelper.putString(Constants.userImage, account.photoUrl.toString())
                startActivity(intentFor<HomeActivity>().newTask())
                finish()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}