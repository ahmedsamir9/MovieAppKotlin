package com.example.movieapp.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.DB.Converters.CastConverter
import com.example.movieapp.DB.dao.*
import com.example.movieapp.model.*
import com.example.movieapp.model.MovieDetailData


@Database(entities = [Movie::class, MoviesRemoteKeys::class,
    UpComingMovies::class,PlayingMovies::class
    , MovieDetailData::class],version = 1,exportSchema = false)
@TypeConverters(CastConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun movieRemoteKeys():MoviesRemoteKeysDao
    abstract fun upComingMoviesDao(): UpComingMoviesDoa
    abstract fun playingNowMoviesDao(): PlayingNowMoviesDoa
    abstract fun movieDetailsDao(): MovieDetailsDao

}