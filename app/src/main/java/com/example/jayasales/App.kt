package com.example.jayasales

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
}
object AppContext {
    private lateinit var _app: App
    val app: App get() = _app
    fun init(app: App) {
        _app = app
    }
}