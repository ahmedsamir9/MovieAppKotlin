package com.example.movieapp.ui.HomeScreen

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.HomeRepository.HomeRepository
import com.example.movieapp.ui.HomeScreen.states.HomeStateEvent
import com.example.movieapp.ui.HomeScreen.states.HomeStateEvent.*
import com.example.movieapp.ui.HomeScreen.states.HomeViewState
import com.example.movieapp.ui.HomeScreen.states.HomeViewState.*
import com.example.movieapp.utils.Resources
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor( val repository: HomeRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val _viewState = MutableLiveData<Flow<PagingData<Movie>>>()
    val viewState :LiveData<Flow<PagingData<Movie>>>
        get() = _viewState

    fun getTopRatedMovies():Flow<PagingData<Movie>>{
       return repository.getTopRatedMovies().cachedIn(viewModelScope)
    }
    fun getPlayNowMovies():Flow<PagingData<Movie>>{
        return repository.getPlayNowMovies().cachedIn(viewModelScope)
    }
    fun getUpComingMovies():Flow<PagingData<Movie>>{
        return repository.getUpcomingMovies().cachedIn(viewModelScope)
    }




    override fun onCleared() {
        super.onCleared()

    }
}
