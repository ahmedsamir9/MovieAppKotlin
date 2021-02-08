package com.example.movieapp.api

import com.example.movieapp.model.ActorResponse
import com.example.movieapp.model.MovieActorResponse
import com.example.movieapp.model.MovieDetails.Credits.MovieCredits
import com.example.movieapp.model.MovieDetails.MovieDetails
import com.example.movieapp.model.MovieDetails.Trailer.TrailerResponse
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.searchmodel.people.PeopleSearchResponse
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Constant.API_KEY
import com.example.movieapp.utils.Constant.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Path
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
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id :Int,@Query("api_key")apiKey :String = API_KEY
                                , @Query("language")language:String = LANGUAGE):MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieDetailsCredits(@Path("movie_id") id :Int,@Query("api_key")apiKey :String = API_KEY
                                       , @Query("language")language:String = LANGUAGE):MovieCredits
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieDetailsVideos(@Path("movie_id") id :Int,@Query("api_key")apiKey :String = API_KEY
                                      , @Query("language")language:String = LANGUAGE):TrailerResponse
    @GET("movie/{movie_id}/recommendations")
    suspend fun getSimilarMovies(@Path("movie_id") id :Int,@Query("api_key")apiKey :String = API_KEY
                                 , @Query("language")language:String = LANGUAGE,@Query("page") page:Int = 1):MovieResponse
    @GET("search/person")
    suspend fun searchForPeople(@Query("query") word :String, @Query("api_key")apiKey :String = API_KEY
                                , @Query("language")language:String = LANGUAGE, @Query("page") page:Int = 1):PeopleSearchResponse
    @GET("search/movie")
    suspend fun searchForMovies(@Query("query") word :String, @Query("api_key")apiKey :String = API_KEY
                                , @Query("language")language:String = LANGUAGE, @Query("page") page:Int = 1):MovieResponse
    @GET("person/{person_id}")
    suspend fun getActor(@Path("person_id") id:Int, @Query("api_key")apiKey :String = API_KEY
                         , @Query("language")language:String = LANGUAGE):ActorResponse
    @GET("person/{person_id}/movie_credits")
    suspend fun getActorMovies(@Path("person_id") id:Int, @Query("api_key")apiKey :String = API_KEY
                               , @Query("language")language:String = LANGUAGE):MovieActorResponse
}