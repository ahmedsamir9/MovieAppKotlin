package com.example.movieapp.repository.MovieDetailsRepositry

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
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.utils.Constant.PLAYING_NOW_CATEGORY
import com.example.movieapp.utils.Constant.TOP_Rated_CATEGORY
import com.example.movieapp.utils.Constant.UP_COMING_CATEGORY
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList

@OptIn(ExperimentalPagingApi::class)
class SimilarMoviesRemoteMediator(
    private val movieName: String,
    private val movieId:Int,
    private val moviesService: MoviesServices,
    private val moviesDatabase: AppDatabase
) : RemoteMediator<Int, SimilarMovie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SimilarMovie>
    ): MediatorResult {
        return try {
            // 1
            val loadKeys= when (loadType) {
                LoadType.REFRESH ->null
                LoadType.PREPEND -> {return MediatorResult.Success(endOfPaginationReached = true)}
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getMovieRemoteKeys(movieName)
                }
            }

            // 2
            var key = loadKeys?.afterIndex?:1
            if (loadType == LoadType.REFRESH ){
                val newkey = getMovieRemoteKeys(movieName)?.afterIndex?.minus(1)
                key = newkey?:key
            }
            val response = fetchCategory(key)
            val data = response!!
            val next = key+ 1
            moviesDatabase.withTransaction {
               moviesDatabase.movieRemoteKeys()
                    .insertKeys(MoviesRemoteKeys(0, next,key, movieName))
                moviesDatabase.similarMovieDao().insertMovies(data)
            }
            val isEmpty = data .isEmpty()
            MediatorResult.Success(endOfPaginationReached = !isEmpty)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }
    private suspend fun getMovieRemoteKeys(MovieName :String): MoviesRemoteKeys? {
        return moviesDatabase.movieRemoteKeys().getMoviesKeys(MovieName).firstOrNull()
    }
    private suspend fun fetchCategory(pageNumber:Int):List<SimilarMovie>{
        val response = moviesService.getSimilarMovies(id =movieId ,page =pageNumber )
        val list = ArrayList<SimilarMovie>()
        response.results?.forEach {
            list.add(
                SimilarMovie(it.backdropPath,0,it.id,
                it.originalTitle,it.posterPath,it.title,movieName, it.voteAverage
                )
            )
        }
        return list
    }

}