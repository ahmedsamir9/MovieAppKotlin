package com.example.movieapp.api

import com.example.movieapp.model.MovieResponse
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Constant.API_KEY
import com.example.movieapp.utils.Constant.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesServices {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key")apiKey :String = API_KEY, @Query("language")language:String = LANGUAGE
                                 , @Query("page") page:Int = 1):MovieResponse
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key")apiKey :String = API_KEY, @Query("language")language:String = LANGUAGE
                                  , @Query("page") page:Int):MovieResponse
    @GET("movie/now_playing")
    suspend fun getPlayingNowMovies(@Query("api_key")apiKey :String = API_KEY, @Query("language")language:String = LANGUAGE
                                    , @Query("page") page:Int):MovieResponse
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key")apiKey :String = API_KEY, @Query("language")language:String = LANGUAGE
                                  , @Query("page") page:Int):MovieResponse
}