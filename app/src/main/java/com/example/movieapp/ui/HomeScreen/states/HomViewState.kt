package com.example.movieapp.ui.HomeScreen.states

import androidx.paging.PagingData
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.Resources
import kotlinx.coroutines.flow.Flow


sealed class HomeViewState {
    class TopRated(val topRated: Flow<PagingData<Movie>>): HomeViewState()
     class UPComing(val upcoming:Flow<PagingData<Movie>>): HomeViewState()
    class PlayingNow(val playNow:Flow<PagingData<Movie>>): HomeViewState()
    class Popular(val popular: Flow<Resources<List<Movie>>>): HomeViewState()
    object Loading :HomeViewState()
    object Idle :HomeViewState()
}