package com.example.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieAPP:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}