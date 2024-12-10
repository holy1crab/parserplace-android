package ru.tinyad.parserplace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
