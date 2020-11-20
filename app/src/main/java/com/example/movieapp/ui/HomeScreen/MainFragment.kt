package com.example.movieapp.ui.HomeScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.ui.HomeScreen.Adapters.MainAdapter
import com.example.movieapp.ui.HomeScreen.Adapters.PopularMovieAdapter
import com.example.movieapp.ui.HomeScreen.Adapters.UpComingAdapter
import com.example.movieapp.ui.LoadingAdapter
import com.example.movieapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var mainFragmentBinder: MainFragmentBinding
    private lateinit var upComingAdapter: UpComingAdapter
    private lateinit var topRatedAdapter: MainAdapter
    private lateinit var playingNowAdapter:MainAdapter
    private lateinit var popularMovieAdapter: PopularMovieAdapter
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragmentBinder = DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false)
        upComingAdapter = UpComingAdapter()
        topRatedAdapter = MainAdapter()
        playingNowAdapter = MainAdapter()
        popularMovieAdapter = PopularMovieAdapter(null)
        lifecycle.addObserver(mainViewModel)
        handleViewStates()
        setUpView()

        return mainFragmentBinder.root
    }

    override fun onStart() {
        super.onStart()
        getUpComingMovies()
        getTopRatedMovie()
        subscribeToData()
        getPlayingNowMovies()
    }

    private fun getTopRatedMovie(){

        lifecycleScope.launch {
            mainViewModel.getTopRatedMovies().flowOn(Dispatchers.Default).collect{
                if (it==null) {Log.d("lolo",it.toString())}
                topRatedAdapter.submitData(it)
            }
        }
    }

    private  fun subscribeToData(){mainViewModel.popularMovies.observe(viewLifecycleOwner, Observer {
        when(it.status){
            Status.SUCCESS -> it.data?.let {list ->

                    mainFragmentBinder.popularProgress.visibility= View.GONE
                    mainFragmentBinder.popularRteyBtn.visibility= View.GONE
                     popularMovieAdapter.onChange(list) }
            Status.ERROR-> {
                mainFragmentBinder.popularRteyBtn.visibility= View.VISIBLE
                mainFragmentBinder.popularRteyBtn.setOnClickListener { retryToConnect() }
            }
            Status.LOADING->{
                mainFragmentBinder.popularProgress.visibility= View.VISIBLE
            }
        }
    })}
    private fun getUpComingMovies(){
        lifecycleScope.launch {
            mainViewModel.getUpComingMovies().flowOn(Dispatchers.Default).collectLatest{
                if (it==null) {Log.d("lolo",it.toString())}
              upComingAdapter.submitData(it)
            }
        }

    }
    private fun getPlayingNowMovies(){
        lifecycleScope.launch {
            mainViewModel.getPlayNowMovies().flowOn(Dispatchers.Default).collectLatest{

                if (it==null) {Log.d("lolo",it.toString())}
                playingNowAdapter.submitData(it)
            }
        }

    }
    private fun setUpView(){
        this.mainFragmentBinder.upComingRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = upComingAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter{upComingAdapter.retry()},
                footer = LoadingAdapter{upComingAdapter.retry()}
            )
        }
        this.mainFragmentBinder.PlayingNowRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)

            adapter = playingNowAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter{playingNowAdapter.retry()},
                footer = LoadingAdapter{playingNowAdapter.retry()}
            )
        }
        this.mainFragmentBinder.topRatedRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = topRatedAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter{topRatedAdapter.retry()},
                footer = LoadingAdapter{topRatedAdapter.retry()}
            )
        }
        this.mainFragmentBinder.viewpager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = popularMovieAdapter
            val pageMargin =
                resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
            val pageOffset =
                resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding =false
            setPageTransformer { page, position ->

                val myOffset = position * -(3* pageOffset + pageMargin)
                if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -myOffset
                    } else {
                        page.translationX = myOffset
                    }
                } else {
                    page.translationY = myOffset
                }
            }
        }
    }
    private fun handleViewStates(){
        playingNowAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
            // Show ProgressBar
                mainFragmentBinder.playnowProgress.visibility = View.VISIBLE
            else {
                // Hide ProgressBar

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                if(errorState == null){
                    mainFragmentBinder.playnowProgress.visibility = View.GONE
                    mainFragmentBinder.playnowRteyBtn.visibility = View.GONE
                }
                else
                {
                    mainFragmentBinder.playnowProgress.visibility = View.VISIBLE
                    mainFragmentBinder.playnowRteyBtn.visibility = View.VISIBLE
                    mainFragmentBinder.playnowRteyBtn.setOnClickListener {
                        retryToConnect()
                    }
                }

            }
        }
        topRatedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
            // Show ProgressBar
                mainFragmentBinder.topRatedProgress.visibility = View.VISIBLE
            else {
                // Hide ProgressBar

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                if(errorState == null){
                    mainFragmentBinder.topRatedProgress.visibility = View.GONE
                    mainFragmentBinder.topRatedRteyBtn.visibility = View.GONE
                }
                else
                {
                    mainFragmentBinder.topRatedProgress.visibility = View.VISIBLE
                    mainFragmentBinder.topRatedRteyBtn.visibility = View.VISIBLE
                    mainFragmentBinder.topRatedRteyBtn.setOnClickListener {
                        retryToConnect()
                    }
                }

            }
        }
       upComingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
            // Show ProgressBar
                mainFragmentBinder.upcomingProgress.visibility = View.VISIBLE
            else {
                // Hide ProgressBar

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                if(errorState == null){
                    mainFragmentBinder.upcomingProgress.visibility = View.GONE
                    mainFragmentBinder.upcomingRteyBtn.visibility = View.GONE
                }
                else
                {
                    mainFragmentBinder.upcomingProgress.visibility = View.VISIBLE
                    mainFragmentBinder.upcomingRteyBtn.visibility = View.VISIBLE
                    mainFragmentBinder.upcomingRteyBtn.setOnClickListener {
                        retryToConnect()
                    }
                }

            }
        }
    }

private fun retryToConnect(){
    playingNowAdapter.retry()
    topRatedAdapter.retry()
    mainViewModel.getPopularMovies()
    upComingAdapter.retry()
}

}

