package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.model.searchmodel.SearchItem

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(list:List<SearchItem>)
    @Query("select * from searchitem where keyword like '%'|| :word|| '%'")
    fun searchForItem(word:String): PagingSource<Int, SearchItem>
    @Query("delete from searchitem")
    fun deleteAllData()

}