package com.example.movieapp.DB.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.model.ActorData
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Constant.POPULAR_CATEGORY
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor:ActorData)
    @Query("select * from actordata where id =:actorId " )
    fun getActorData(actorId: Int ):Flow<ActorData>
    @Query("delete from ActorData")
    fun deleteAllData()

}