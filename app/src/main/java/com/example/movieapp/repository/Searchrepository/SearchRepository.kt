package com.example.movieapp.repository.Searchrepository

import com.example.movieapp.DB.AppDatabase
import com.example.movieapp.api.MoviesServices

data class SearchRepository constructor(private val localDataSource: AppDatabase, private val remoteDataSource: MoviesServices){

}

