package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.Movie
import com.example.movieapp.model.SimilarMovie
@Dao
interface SimilarMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies:List<SimilarMovie>)
    @Query("select * from  similarmovie where similarTo like '%' || :movieName || '%' " )
    fun getSimilarMovies(movieName: String ): PagingSource<Int, SimilarMovie>
}