package com.example.movieapp.repository.HomeRepository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MoviesRemoteKeys
import com.example.movieapp.utils.Constant.PLAYING_NOW_CATEGORY
import com.example.movieapp.utils.Constant.TOP_Rated_CATEGORY
import com.example.movieapp.utils.Constant.UP_COMING_CATEGORY
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val category: String,
    private val moviesService: MoviesServices,
    private val moviesDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            // 1
            val loadKeys= when (loadType) {
                LoadType.REFRESH ->null
                LoadType.PREPEND -> {return MediatorResult.Success(endOfPaginationReached = true)}
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getMovieRemoteKeys(category = category)
                }
            }

            // 2
            val key = loadKeys?.afterIndex?:1
            val response = fetchCategory(key)
            val data = response!!
            val next = key+ 1
            moviesDatabase.withTransaction {
               moviesDatabase.movieRemoteKeys()
                    .insertKeys(MoviesRemoteKeys(0, next,key, category))
                moviesDatabase.movieDao().insertMovies(data)
            }
            val isEmpty = data .isEmpty()
            MediatorResult.Success(endOfPaginationReached = !isEmpty)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }
    private suspend fun getMovieRemoteKeys(category :String): MoviesRemoteKeys? {
        return moviesDatabase.movieRemoteKeys().getMoviesKeys(category).firstOrNull()

    }
    private suspend fun fetchCategory(pageNumber:Int):List<Movie>?{
        val response = when(category){
            TOP_Rated_CATEGORY -> moviesService.getTopRatedMovies(page =pageNumber )
            UP_COMING_CATEGORY -> moviesService.getUpComingMovies(page = pageNumber)
            PLAYING_NOW_CATEGORY -> moviesService.getPlayingNowMovies(page = pageNumber)
            else -> null
        }
        println("********************************************************************")
        println(response.toString())
        val list = ArrayList<Movie>()
        response!!.results?.forEach {
            list.add(
                Movie(it.backdropPath,it.id,
                it.originalTitle,it.posterPath,it.title,category, it.voteAverage
                )
            )
            println("********************************************************************")
            println(it.toString())
        }
        return list
    }

}