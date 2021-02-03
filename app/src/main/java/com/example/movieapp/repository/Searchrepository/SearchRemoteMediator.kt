package com.example.movieapp.repository.Searchrepository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.MoviesRemoteKeys
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.model.searchmodel.SearchItem
import com.example.movieapp.utils.Constant
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val word: String,
    private val moviesService: MoviesServices,
    private val moviesDatabase: AppDatabase
) : RemoteMediator<Int,SearchItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchItem>
    ): MediatorResult {
        return try {
            val loadKeys= when (loadType) {
                LoadType.REFRESH ->null
                LoadType.PREPEND -> {return MediatorResult.Success(endOfPaginationReached = true)}
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getMovieRemoteKeys(word)
                }
            }

            // 2
            var key = loadKeys?.afterIndex?:1
            if (loadType == LoadType.REFRESH ){
                val newkey = getMovieRemoteKeys(word)?.afterIndex?.minus(1)
                key = newkey?:key
            }
            val response = fetchSearchItems(key)
            val data = response!!
            val next = key+ 1
            moviesDatabase.withTransaction {
               moviesDatabase.movieRemoteKeys()
                    .insertKeys(MoviesRemoteKeys(0, next,key, word))
                moviesDatabase.searchDao().insertSearch(data)
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
    private suspend fun fetchSearchItems(pageNumber:Int):List<SearchItem>{
        val movieResponse = moviesService.searchForMovies(word = word,page = pageNumber)
        val result = ArrayList<SearchItem>()
        movieResponse.results!!.forEach {
            val searchItem =
                it.id?.let { id -> it.title?.let { title ->
                    it.posterPath?.let {posterPath->
                        SearchItem(id,
                            title,word,posterPath,Constant.MEDIA_TYPE_MOVIE)
                    }
                } }
            searchItem?.let { searchItem -> result.add(searchItem) }
        }
        val peopleResponse = moviesService.searchForPeople(word,page = pageNumber)
        peopleResponse.results?.forEach {
            val searchItem =
                it.id?.let { id -> it.name?.let { name ->
                    it.profilePath?.let { profilePath ->
                        SearchItem(id,
                            name,word,profilePath,Constant.MEDIA_TYPE_PERSON)
                    }
                } }
            searchItem?.let { searchItem -> result.add(searchItem) }
        }
        return result.shuffled()
    }

}