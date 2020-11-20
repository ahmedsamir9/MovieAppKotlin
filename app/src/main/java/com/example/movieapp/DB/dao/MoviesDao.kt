package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Constant.POPULAR_CATEGORY
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies:List<Movie>)
    @Query("select * from movie where category like '%' || :cate || '%' " )
    fun getMovies(cate: String ):PagingSource<Int,Movie>
    @Query("select * from movie where category like '%' || :cate || '%' " )
    fun getPlayingMovies(cate: String ):PagingSource<Int,Movie>
    @Query("select * from movie where category like '%' || :cate || '%' " )
    fun getPopularMovies(cate: String = POPULAR_CATEGORY):Flow<List<Movie>>
}