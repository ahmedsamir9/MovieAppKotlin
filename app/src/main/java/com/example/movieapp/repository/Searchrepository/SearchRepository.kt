package com.example.movieapp.repository.Searchrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices
import com.example.movieapp.model.searchmodel.SearchItem
import com.example.movieapp.repository.MovieDetailsRepositry.SimilarMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow


data class SearchRepository constructor(private val localDataSource: AppDatabase, private val remoteDataSource: MoviesServices){
        fun getSearchResults(word :String): Flow<PagingData<SearchItem>> {
            return Pager(
                    config =  PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false) ,
            remoteMediator =
                SearchRemoteMediator(word,remoteDataSource,localDataSource),
            pagingSourceFactory = {localDataSource.searchDao().searchForItem(word)}
            ).flow
        }
}

