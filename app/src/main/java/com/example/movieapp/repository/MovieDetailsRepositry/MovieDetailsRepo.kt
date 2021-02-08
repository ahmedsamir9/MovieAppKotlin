package com.example.movieapp.repository.MovieDetailsRepositry

import NetworkBoundResource
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieDetailData
import com.example.movieapp.model.MovieDetails.MovieDetails
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.repository.HomeRepository.MoviesRemoteMediator

import com.example.movieapp.utils.ApiResult
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Resources
import com.example.movieapp.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MovieDetailsRepo constructor(private val localDataSource: AppDatabase, private val remoteDataSource: MoviesServices){

    public fun getMovieDetails(id :Int): Flow<Resources<MovieDetailData>> {
    return object :NetworkBoundResource<MovieDetailData, MovieDetails>(){
        override suspend fun saveNetworkResult(item: MovieDetails) {
            val response = safeApiCall { remoteDataSource.getMovieDetailsCredits(id = id)}
            when(response){
                is ApiResult.Success ->{
                    val movieShowData = MovieDetailData(item.id!!,item.title!!,
                        item.overview!!,response.value.cast!!,item.voteAverage!!,item.video!!,item.backdropPath!!,item.posterPath!!)
                    if(item.video == true){
                        val trailers = remoteDataSource.getMovieDetailsVideos(id = id)
                        Log.d("smsm",trailers.trailerResults.toString())
                        movieShowData.videoUrlOnYoutube = trailers.trailerResults!!.get(0).key!!
                    }
                    localDataSource.movieDetailsDao().insertMovies(movieShowData)
                }
            }
        }

        override fun shouldFetch(data: MovieDetailData?): Boolean {

            return  data == null
        }

        override fun loadFromDb(): Flow<MovieDetailData> {

           return flow { emit(localDataSource.movieDetailsDao().getMovieDetails(id)) }
        }

        override suspend fun fetchFromNetwork(): ApiResult<MovieDetails> {

            return safeApiCall { remoteDataSource.getMovieDetails(id = id) }
        }

    }.asFlow()
    }
   fun getSimilarMovies(movieName :String,movieId:Int): Flow<PagingData<SimilarMovie>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false) ,
            remoteMediator =SimilarMoviesRemoteMediator(movieName,movieId,remoteDataSource,localDataSource),
            pagingSourceFactory = {localDataSource.similarMovieDao().getSimilarMovies(movieName)}
        ).flow
    }

}