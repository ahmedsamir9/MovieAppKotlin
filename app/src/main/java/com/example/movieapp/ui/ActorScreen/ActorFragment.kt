package com.example.movieapp.ui.ActorScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ActorFragmentBinding
import com.example.movieapp.model.ActorMovies
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.Status
import com.example.movieapp.utils.visible

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorFragment : Fragment() {
    private val viewModel: ActorViewModel by viewModels()
    private val args: ActorFragmentArgs by navArgs()
    private lateinit var actorFragmentBinding:ActorFragmentBinding
    private lateinit var moviesAdapter:ActorMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actorFragmentBinding=DataBindingUtil.inflate(inflater,R.layout.actor_fragment,container,false)
        moviesAdapter = ActorMoviesAdapter(navigateToMovie())
        initMoviesRecyclerView()
        onClick()
        return actorFragmentBinding.root
    }


    override fun onStart() {
        super.onStart()
        args.actorId.let { viewModel.getActorData(it) }
        subscribeToLiveData()
    }

    private fun subscribeToLiveData(){
        viewModel.actorData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    actorFragmentBinding.motionLayoutHeader.actor =it.data
                    actorFragmentBinding.scrollable.actor =it.data
                    actorFragmentBinding.backactor.visible(false)
                    actorFragmentBinding.motionActor.visible(true)
                    hideShimmerEffect()
                    toggleErrorContent(false)
                    moviesAdapter.submitList(it.data?.movies)
                }
                Status.LOADING->{
                    toggleErrorContent(false)
                    actorFragmentBinding.shimmerActor.visible(true)
                    actorFragmentBinding.shimmerActor.startShimmerAnimation()
                }
                Status.ERROR->{
                    toggleErrorContent(true)
                    hideShimmerEffect()
                    actorFragmentBinding.errorMessage.text=it.message
                }
            }
        })
    }
    private fun initMoviesRecyclerView(){
        actorFragmentBinding.scrollable.moviesRv.apply {
            adapter=moviesAdapter
            layoutManager =GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
        }
    }
    private fun navigateToMovie(): ActorMoviesAdapter.Interaction {
        return object:ActorMoviesAdapter.Interaction{
            override fun onItemSelected(item: ActorMovies) {
            val action = item.id?.let {
                ActorFragmentDirections.actionActorFragmentToDetailsFragment(
                    it
                )
            }
                action?.let { findNavController().navigate(it) }
            }

        }
    }
    private var hasMotionScrolled = false

    override fun onResume() {
        super.onResume()
        if (hasMotionScrolled) actorFragmentBinding.motionActor.progress = MOTION_TRANSITION_COMPLETED
    }

    override fun onPause() {
        super.onPause()
        hasMotionScrolled =  actorFragmentBinding.motionActor.progress > MOTION_TRANSITION_INITIAL
    }
    companion object {
        private const val MOTION_TRANSITION_COMPLETED = 1F
        private const val MOTION_TRANSITION_INITIAL = 0F
    }
    private fun hideShimmerEffect() {
        actorFragmentBinding.shimmerActor.stopShimmerAnimation()
        actorFragmentBinding.shimmerActor.visible(false)
    }
    private fun onClick(){
        actorFragmentBinding.backactor.setOnClickListener {
            findNavController().popBackStack()
        }
        actorFragmentBinding.motionLayoutHeader.back.setOnClickListener {
            findNavController().popBackStack()
        }
        actorFragmentBinding.btnRetry.setOnClickListener {
            args.actorId.let { viewModel.getActorData(it) }
        }
    }
    private fun toggleErrorContent(isVisible:Boolean){
        actorFragmentBinding.errorContent.visible(isVisible)
    }
}