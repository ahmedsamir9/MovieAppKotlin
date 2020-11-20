package com.example.movieapp.ui.HomeScreen

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.model.Movie

import com.example.movieapp.model.UpComingMovies
import com.example.movieapp.repository.HomeRepository.HomeRepository
import com.example.movieapp.ui.HomeScreen.states.HomeStateEvent
import com.example.movieapp.ui.HomeScreen.states.HomeStateEvent.*
import com.example.movieapp.ui.HomeScreen.states.HomeViewState
import com.example.movieapp.ui.HomeScreen.states.HomeViewState.*
import com.example.movieapp.utils.Resources
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor( val repository: HomeRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel(),LifecycleObserver {
    val _popularMovies = MutableLiveData<Resources<List<Movie>>>()
    val popularMovies :LiveData<Resources<List<Movie>>>
        get() = _popularMovies

    fun getTopRatedMovies():Flow<PagingData<Movie>>{
       return repository.getTopRatedMovies().cachedIn(viewModelScope)
    }
    fun getPlayNowMovies():Flow<PagingData<Movie>>{
        return repository.getPlayNowMovies().cachedIn(viewModelScope)
    }
    fun getUpComingMovies():Flow<PagingData<UpComingMovies>>{
        return repository.getUpcomingMovies().cachedIn(viewModelScope)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getPopularMovies(){
        _popularMovies.postValue(Resources.loading(null))
        viewModelScope.launch {
            repository.getPopularMovies().collect {
                _popularMovies.postValue(it)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

    }
}
