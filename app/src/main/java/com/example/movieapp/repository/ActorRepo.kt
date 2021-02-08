package com.example.movieapp.repository

import NetworkBoundResource
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.ActorData
import com.example.movieapp.model.ActorMovies
import com.example.movieapp.model.ActorResponse
import com.example.movieapp.utils.ApiResult
import com.example.movieapp.utils.Resources
import com.example.movieapp.utils.safeApiCall
import kotlinx.coroutines.flow.Flow

data class ActorRepository constructor(private val localDataSource: AppDatabase, private val remoteDataSource: MoviesServices) {
    public suspend fun getActorData(actorId:Int): Flow<Resources<ActorData>> {
        return object :NetworkBoundResource<ActorData,ActorResponse>(){
            override suspend fun saveNetworkResult(item: ActorResponse) {
                val response = safeApiCall { remoteDataSource.getActorMovies(actorId)}
                when(response){
                    is ApiResult.Success->{
                        val movies = mutableListOf<ActorMovies>()
                        response.value.cast?.forEach { movie->
                            val actorMovie =
                                movie.posterPath?.let {
                                    movie.voteAverage?.let { vote ->
                                        ActorMovies(movie.id,movie.title,
                                            it, vote
                                        )
                                    }
                                }
                            if (actorMovie != null) {
                                movies.add(actorMovie)
                            }
                        }
                        val actorData =ActorData(item.name,item.id!!,item.biography,item.profilePath,movies)
                        localDataSource.actorDataDao().insertActor(actorData)
                    }
                }
            }

            override fun shouldFetch(data: ActorData?): Boolean {
               return data ==null
            }

            override fun loadFromDb(): Flow<ActorData> {
              return localDataSource.actorDataDao().getActorData(actorId)
            }

            override suspend fun fetchFromNetwork(): ApiResult<ActorResponse> {
               return safeApiCall { remoteDataSource.getActor(actorId) }
            }

        }.asFlow()
    }
}