package com.example.movieapp

import android.app.Application
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieAPP:Application() {
    override fun onCreate() {
        super.onCreate()
    }


}