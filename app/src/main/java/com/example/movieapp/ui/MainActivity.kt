package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.movieapp.R
import com.example.movieapp.work.DeleteWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorkManager.getInstance(this).enqueue(makeDeleteDataRequest())
    }
    private fun deleteWorkConstraint(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
    }
    private fun makeDeleteDataRequest(): OneTimeWorkRequest {
        return  OneTimeWorkRequestBuilder<DeleteWorker>()
            .setConstraints(deleteWorkConstraint())
            .build()
    }
}