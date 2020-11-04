package com.example.movieapp.ui.HomeScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.model.Movie
import java.util.zip.Inflater

class UpCommingAdapter:PagingDataAdapter<Movie,UpCommingAdapter.UpComingViewHolder>(
    upComingDiffUtils) {



class UpComingViewHolder(val binding :MovieItemBinding) :RecyclerView.ViewHolder(binding.root){
    fun onBind(item:Movie){
        binding.movie =item
    }

}

    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater,parent,false)
        return UpComingViewHolder(binding)
    }
    companion object{
        val upComingDiffUtils = object :DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return  oldItem == newItem
            }

        }

    }

}





