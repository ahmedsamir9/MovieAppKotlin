package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.Movie
import com.example.movieapp.model.PlayingMovies
import com.example.movieapp.model.UpComingMovies
import com.example.movieapp.utils.Constant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayingNowMoviesDoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies:List<PlayingMovies>)
    @Query("select * from PlayingMovies " )
    fun getMovies(): PagingSource<Int, PlayingMovies>
    @Query("delete from playingmovies")
    fun deleteAllData()


}