package com.example.movieapp.ui.Search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.model.searchmodel.SearchItem
import com.example.movieapp.repository.Searchrepository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn

class SearchViewModel @ViewModelInject constructor( private val searchRepository: SearchRepository) :
    ViewModel() {
    public fun getSearchResults(word:String): Flow<PagingData<SearchItem>> {
        return searchRepository.getSearchResults(word)
            .flowOn(Dispatchers.IO).cachedIn(viewModelScope)
    }
}