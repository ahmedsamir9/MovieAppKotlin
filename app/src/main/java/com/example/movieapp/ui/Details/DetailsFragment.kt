package com.example.movieapp.ui.Details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.DetailsFragmentBinding
import com.example.movieapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var detailsFragmentBinder: DetailsFragmentBinding
    private val detailViewModel: MovieDetailViewModel by viewModels()
    private val args:DetailsFragmentArgs by navArgs()
    private var movieID by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsFragmentBinder = DataBindingUtil.inflate(inflater,R.layout.details_fragment,container,false)

        return detailsFragmentBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieID =args.moiveId

    }

    override fun onStart() {
        super.onStart()
        detailViewModel.getMovieData(movieID)
        subscribeToLiveData()
    }
    private fun subscribeToLiveData(){
        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    detailsFragmentBinder.movieData = it.data
                    detailsFragmentBinder.content.movieDetails =it.data
                }
            }
        })
    }
}