package com.daniyal.todo_mvvm.app

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.multidex.MultiDex
import com.daniyal.todo_mvvm.utilities.GeneralHelper
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

        MultiDex.install(this)
    }

}