package com.example.movieapp.module

import android.content.Context
import androidx.room.Room
import com.example.movieapp.DB.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule{
    @Singleton
    @Provides
    fun  dataBaseProvider(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,AppDatabase::class.java,"MovieDB").allowMainThreadQueries().build()
    @Singleton
    @Provides
    fun provideMoviesDao(dataBase: AppDatabase)=dataBase.movieDao()
    @Singleton
    @Provides
    fun provideMoviesRemoteKeysDao(dataBase: AppDatabase)=dataBase.movieRemoteKeys()
    @Singleton
    @Provides
    fun provideUpComingMoviesDao(dataBase: AppDatabase)=dataBase.upComingMoviesDao()
    @Singleton
    @Provides
    fun providePlayingNowMoviesDao(dataBase: AppDatabase)=dataBase.playingNowMoviesDao()
    @Singleton
    @Provides
    fun provideMovieDetailsDao(dataBase: AppDatabase)=dataBase.movieDetailsDao()
    @Singleton
    @Provides
    fun provideSimilarMovieDao(dataBase: AppDatabase)=dataBase.similarMovieDao()
    @Singleton
    @Provides
    fun provideSearchDao(dataBase: AppDatabase)=dataBase.searchDao()
}