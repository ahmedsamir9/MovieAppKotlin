package com.example.movieapp.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.DB.dao.MoviesDao
import com.example.movieapp.DB.dao.MoviesRemoteKeysDao
import com.example.movieapp.DB.dao.UpComingMoviesDoa
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MoviesRemoteKeys

import com.example.movieapp.model.UpComingMovies


@Database(entities = [Movie::class, MoviesRemoteKeys::class,UpComingMovies::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun movieRemoteKeys():MoviesRemoteKeysDao
    abstract fun upComingMoviesDao(): UpComingMoviesDoa

}