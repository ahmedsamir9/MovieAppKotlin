package com.example.movieapp.DB.dao

import androidx.room.*

import com.example.movieapp.model.MovieDetailData
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: MovieDetailData)
    @Query("select * from MovieDetailData where id=:movieId")
    suspend fun getMovieDetails(movieId:Int): MovieDetailData
}