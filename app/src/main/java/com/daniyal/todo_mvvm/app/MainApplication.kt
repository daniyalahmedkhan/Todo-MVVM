package com.daniyal.todo_mvvm.app

import android.app.Application
import android.content.ContextWrapper
import com.daniyal.todo_mvvm.utilities.PrefsHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        // Initialize the Prefs class
        PrefsHelper.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}