package com.example.movieapp.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.movieapp.DB.AppDatabase

class DeleteWorker(val appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val appDatabase =AppDatabase.getInstance(appContext)
     try {
         appDatabase.upComingMoviesDao().deleteAllData()
         appDatabase.similarMovieDao().deleteAllData()
         appDatabase.movieRemoteKeys().deleteAllData()
         appDatabase.movieDao().deleteAllData()
         appDatabase.playingNowMoviesDao().deleteAllData()
         appDatabase.searchDao().deleteAllData()
         appDatabase.actorDataDao().deleteAllData()
         appDatabase.movieDetailsDao().deleteAllData()
         return Result.success()
     }catch (ex:Exception){
         return Result.failure()
     }
    }
}