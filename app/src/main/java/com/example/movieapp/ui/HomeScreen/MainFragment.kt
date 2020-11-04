package com.example.movieapp.ui.HomeScreen

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.ui.HomeScreen.states.HomeStateEvent
import com.example.movieapp.ui.HomeScreen.states.HomeViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var mainFragmentBinder: MainFragmentBinding
    private lateinit var upComingAdapter:UpCommingAdapter
    private lateinit var topRatedAdapter:UpCommingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragmentBinder = DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false)
        upComingAdapter = UpCommingAdapter()

        setUpRecyclerview()
        subscribeToData()

        return mainFragmentBinder.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topRatedAdapter = UpCommingAdapter()
        lifecycleScope.launch {
            mainViewModel.getTopRatedMovies().collectLatest{
                Log.d("topRated",it.toString())
                topRatedAdapter.submitData(it)
            }
        }
    }

    private fun subscribeToData(){
        lifecycleScope.launch {

            mainViewModel.getUpComingMovies().collectLatest{
                Log.d("daa",it.toString())
              upComingAdapter.submitData(it)
            }
        }

    }

    private fun setUpRecyclerview(){
        this.mainFragmentBinder.upComingRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = upComingAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter{upComingAdapter.retry()},
                footer = LoadingAdapter{upComingAdapter.retry()}
            )
        }
        this.mainFragmentBinder.topRatedRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = topRatedAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter{topRatedAdapter.retry()},
                footer = LoadingAdapter{topRatedAdapter.retry()}
            )
        }

    }
    }

