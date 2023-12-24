package com.example.homework17revised2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var application: Application
            private set
    }
    override fun onCreate() {
        super.onCreate()
        application = this
    }
}