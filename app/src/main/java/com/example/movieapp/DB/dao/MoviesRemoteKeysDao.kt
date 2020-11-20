package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MoviesRemoteKeys

@Dao
interface MoviesRemoteKeysDao {
    @Insert
    suspend fun insertKeys(key:MoviesRemoteKeys)
    @Query("select * from MoviesRemoteKeys where category like '%' || :cate || '%' order by pk Desc " )
     fun getMoviesKeys(cate: String ):List<MoviesRemoteKeys>

}