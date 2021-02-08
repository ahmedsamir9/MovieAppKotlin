package com.example.movieapp.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.DB.Converters.DataTypeConverter
import com.example.movieapp.DB.dao.*
import com.example.movieapp.model.*
import com.example.movieapp.model.MovieDetailData
import com.example.movieapp.model.searchmodel.SearchItem


@Database(entities = [Movie::class, MoviesRemoteKeys::class,
    UpComingMovies::class,PlayingMovies::class
    , MovieDetailData::class,SimilarMovie::class,SearchItem::class,ActorData::class],version = 1,exportSchema = false)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun searchDao(): SearchDao
    abstract fun movieRemoteKeys():MoviesRemoteKeysDao
    abstract fun similarMovieDao():SimilarMovieDao
    abstract fun upComingMoviesDao(): UpComingMoviesDoa
    abstract fun playingNowMoviesDao(): PlayingNowMoviesDoa
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun actorDataDao(): ActorDao
    companion object{
        fun getInstance(context: Context)=Room.databaseBuilder(context,AppDatabase::class.java,"MovieDB").allowMainThreadQueries().build()
    }
}