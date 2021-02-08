package com.example.movieapp.ui.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.model.searchmodel.SearchItem
import com.example.movieapp.ui.LoadingAdapter

import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchFragmentBinding :SearchFragmentBinding
    var searchJob: Job?= null
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchFragmentBinding =DataBindingUtil.inflate(inflater,R.layout.search_fragment, container, false)
        initRecyclerView()
        return searchFragmentBinding.root
    }
    override fun onStart() {
        super.onStart()
        searchFragmentBinding.shimmerSearch.startShimmerAnimation()
        getItemFromSearch(searchFragmentBinding.searchForMovie.query.toString())
        getRegisterSearchQuery()
    }
   private fun getRegisterSearchQuery(){

           searchFragmentBinding.searchForMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                   getItemFromSearch(query)
                   return true
               }

               override fun onQueryTextChange(newText: String?): Boolean {
                    getItemFromSearch(newText)
                   return true
               }

           }
           )
   }

    private fun initRecyclerView(){
        searchAdapter = SearchAdapter(onClickOnItems())
        searchFragmentBinding.errorBtn.setOnClickListener {
            searchAdapter.retry()
        }
       handleSearchItemsState()
        searchFragmentBinding.searchRv.apply {
            adapter =searchAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter {searchAdapter.retry()},
                footer =LoadingAdapter {searchAdapter.retry()}
            )
            layoutManager =LinearLayoutManager(context)
        }
    }
    private fun getItemFromSearch(query :String?){
        searchJob?.cancel()
       searchJob = lifecycleScope.launch {
        query?.let {
            if (query.isNotEmpty()){
                viewModel.getSearchResults(query).debounce(2000).distinctUntilChanged().collectLatest {
                    hideShimmerEffect()
                    searchAdapter.submitData(it)

                }
            }
        }
    }

    }
    private fun handleSearchItemsState(){
            this.searchAdapter.addLoadStateListener { loadState ->
                if ( loadState.append.endOfPaginationReached )
                {
               Toast.makeText(context,"Not found",Toast.LENGTH_LONG).show()
                }
                if (loadState.refresh is LoadState.Loading ||
                    loadState.append is LoadState.Loading)
                // Show ProgressBar
                    if( searchAdapter.itemCount <= 0){
                        searchFragmentBinding.shimmerSearch.startShimmerAnimation()
                       searchFragmentBinding.shimmerSearch.visible(true)}
                    else {
                        // Hide ProgressBar
                       hideShimmerEffect()
                        // If we have an error, show a toast
                        val errorState = when {
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        if(errorState == null || searchAdapter.itemCount > 0){
                            hideShimmerEffect()
                            searchFragmentBinding.errorMessage.visible(false)
                            searchFragmentBinding.errorBtn.visible(false)

                        }
                        else
                        {
                            searchFragmentBinding.errorMessage.visible(true)
                            searchFragmentBinding.errorBtn.visible(true)
                            searchFragmentBinding.errorMessage.text=errorState.toString()
                        }

                    }
                if (searchAdapter.itemCount > 0)hideShimmerEffect()
            }

    }


    private fun hideShimmerEffect() {
        searchFragmentBinding.shimmerSearch.stopShimmerAnimation()
        searchFragmentBinding.shimmerSearch.visible(false)
    }

    override fun onPause() {
       hideShimmerEffect()
        super.onPause()
    }
    private fun onClickOnItems(): SearchAdapter.OnSelectItem {
        return object :SearchAdapter.OnSelectItem{
            override fun onClickItem(item: SearchItem) {
                if (item.mediaType == Constant.MEDIA_TYPE_MOVIE){
                    val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(item.id)
                    findNavController().navigate(action)
                }else{
                    val action = SearchFragmentDirections.actionSearchFragmentToActorFragment(item.id)
                    findNavController().navigate(action)
                }
            }

        }
    }
    }

