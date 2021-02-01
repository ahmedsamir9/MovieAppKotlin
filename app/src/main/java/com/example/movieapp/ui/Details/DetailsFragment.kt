package com.example.movieapp.ui.Details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.DetailsFragmentBinding
import com.example.movieapp.model.SimilarMovie
import com.example.movieapp.ui.Details.adapters.CastAdapter
import com.example.movieapp.ui.Details.adapters.SimilarMoviesAdapter
import com.example.movieapp.ui.HorItemDecorator
import com.example.movieapp.ui.LoadingAdapter
import com.example.movieapp.utils.Status
import com.example.movieapp.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var detailsFragmentBinder: DetailsFragmentBinding
    private val detailViewModel: MovieDetailViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var castAdapter: CastAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private var movieID by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsFragmentBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.details_fragment,
            container,
            false
        )
        setUpView()
        return detailsFragmentBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieID = args.moiveId

    }

    override fun onStart() {
        super.onStart()
        detailViewModel.getMovieData(movieID)
        subscribeToLiveData()
        handleClicks()
    }

    private fun subscribeToLiveData() {
        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    hideShimmerEffect()
                    toggleScreenState(true)
                    detailsFragmentBinder.movieData = it.data
                    castAdapter.submitList(it.data?.cast)
                    val rate = it.data?.rate?.toFloat()
                    detailsFragmentBinder.ratingBar.rating = rate!!
                    getSimilarMovies(it.data!!.title, it.data.id)
                    openVideo(it.data.title)
                }
                Status.ERROR -> {
                    toggleScreenState(false)
                    hideShimmerEffect()
                    detailsFragmentBinder.detailsMessage.text = it.message
                }
                Status.LOADING -> {
                    detailsFragmentBinder.detailsShimmer.startShimmerAnimation()
                }
            }
        })
    }

    private fun setUpView() {
        castAdapter = CastAdapter(null)
        detailsFragmentBinder.actorRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = castAdapter
            addItemDecoration(HorItemDecorator(16))
        }

    }

    private fun toggleScreenState(isVisible: Boolean) {
        detailsFragmentBinder.content.isVisible(isVisible)
        detailsFragmentBinder.detailsMessage.isVisible(!isVisible)
        detailsFragmentBinder.retryMovieLoad.isVisible(!isVisible)
    }

    private fun handleClicks() {
        detailsFragmentBinder.retryMovieLoad.setOnClickListener {
            detailViewModel.getMovieData(movieID)
        }
        detailsFragmentBinder.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        detailsFragmentBinder.detailsShimmer.stopShimmerAnimation()
        super.onPause()
    }

    private fun hideShimmerEffect() {
        detailsFragmentBinder.detailsShimmer.stopShimmerAnimation()
        detailsFragmentBinder.detailsShimmer.isVisible(false)
    }

    private fun getSimilarMovies(movieName: String, movieId: Int) {
        setUpSimilarRecyclerView(movieId)
        lifecycleScope.launch {
            detailViewModel.getSimilarMovies(movieName, movieId).flowOn(Dispatchers.IO).collect {
                similarMoviesAdapter.submitData(it)
            }
        }
    }

    private fun navigateToMovie(movieId: Int): SimilarMoviesAdapter.Interaction {
        return object : SimilarMoviesAdapter.Interaction {
            override fun onItemSelected(position: Int, item: SimilarMovie) {
                val action = item.id?.let {
                    DetailsFragmentDirections.actionDetailsFragmentSelf(it)

                }
                action?.let { findNavController().navigate(it) }
            }

        }
    }

    private fun setUpSimilarRecyclerView(movieId: Int) {
        similarMoviesAdapter = SimilarMoviesAdapter(navigateToMovie(movieId))
        detailsFragmentBinder.similarRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = similarMoviesAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { similarMoviesAdapter.retry() },
                footer = LoadingAdapter { similarMoviesAdapter.retry() }
            )
            addItemDecoration(HorItemDecorator(16))
        }
    }
    private fun openVideo(movieName: String){
        detailsFragmentBinder.playFab.setOnClickListener {
            val url = "https://www.cimaclub.in/search?s=$movieName"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}