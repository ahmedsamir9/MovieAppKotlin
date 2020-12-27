package com.example.movieapp.repository.MovieDetailsRepositry

import NetworkBoundResource
import android.util.Log
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.MovieDetailData
import com.example.movieapp.model.MovieDetails.MovieDetails

import com.example.movieapp.utils.ApiResult
import com.example.movieapp.utils.Resources
import com.example.movieapp.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MovieDetailsRepo constructor(private val localDataSource: AppDatabase, private val remoteDataSource: MoviesServices){

    public fun getMovieDetails(id :Int): Flow<Resources<MovieDetailData>> {
    return object :NetworkBoundResource<MovieDetailData, MovieDetails>(){
        override suspend fun saveNetworkResult(item: MovieDetails) {
            Log.d("Soso ","first")
            val response = safeApiCall { remoteDataSource.getMovieDetailsCredits(id = id)}
            when(response){
                is ApiResult.Success ->{
                    Log.d("Soso ","second")
                    val movieShowData = MovieDetailData(item.id!!,item.title!!,
                        item.overview!!,response.value.cast!!,item.voteAverage!!,item.video!!,item.backdropPath!!,item.posterPath!!)
                    if(item.video == true){
                        val trailers = remoteDataSource.getMovieDetailsVideos(id = id)
                        movieShowData.videoUrlOnYoutube = trailers.trailerResults!!.get(0).key!!

                    }
                    localDataSource.movieDetailsDao().insertMovies(movieShowData)
                    Log.d("Soso ","3")
                }
            }
        }

        override fun shouldFetch(data: MovieDetailData?): Boolean {
            Log.d("soso ","foo")
            return  data == null
        }

        override fun loadFromDb(): Flow<MovieDetailData> {
            Log.d("soso ","loa")
           return flow { emit(localDataSource.movieDetailsDao().getMovieDetails(id)) }
        }

        override suspend fun fetchFromNetwork(): ApiResult<MovieDetails> {
            Log.d("Soso ","api")
            return safeApiCall { remoteDataSource.getMovieDetails(id = id) }
        }

    }.asFlow()
    }

}