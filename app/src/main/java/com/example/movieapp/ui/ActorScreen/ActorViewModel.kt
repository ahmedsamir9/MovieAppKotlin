package com.example.movieapp.ui.ActorScreen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ActorData
import com.example.movieapp.model.MovieDetailData
import com.example.movieapp.repository.ActorRepository
import com.example.movieapp.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ActorViewModel @ViewModelInject constructor(private val actorRepository: ActorRepository) : ViewModel() {
    private val _actorData = MutableLiveData<Resources<ActorData>>()
    val actorData : LiveData<Resources<ActorData>>
        get() = _actorData
    fun getActorData(actorID:Int){
        viewModelScope.launch {
            _actorData.postValue(Resources.loading(null))
            actorRepository.getActorData(actorID).flowOn(Dispatchers.Default).collect {
                _actorData.postValue(it)
            }
        }
    }
}