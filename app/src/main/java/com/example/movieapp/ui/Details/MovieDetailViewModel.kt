package com.example.movieapp.ui.Details

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.model.MovieDetailData
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.repository.MovieDetailsRepositry.MovieDetailsRepo
import com.example.movieapp.utils.Resources
import com.example.movieapp.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MovieDetailViewModel@ViewModelInject constructor( private val repository:MovieDetailsRepo, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel(){
    private val _movieDetails = MutableLiveData<Resources<MovieDetailData>>()
    val movieDetails : LiveData<Resources<MovieDetailData>>
        get() = _movieDetails


    public fun getMovieData(id :Int){
        _movieDetails.postValue(Resources.loading(null))
        viewModelScope.launch {
            repository.getMovieDetails(id).flowOn(Dispatchers.Default).collect {
                _movieDetails.postValue(it)
            }
        }
    }
    public fun getSimilarMovies(movieName:String ,movieId:Int): Flow<PagingData<SimilarMovie>> {
        return repository.getSimilarMovies(movieName,movieId).cachedIn(viewModelScope)
    }
}