package com.example.movieapp.ui.HomeScreen.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.OnClickOnItem

class MainAdapter(val click :OnClickOnItem):PagingDataAdapter<Movie, MainAdapter.MainViewHolder>(
    upComingDiffUtils
) {



class MainViewHolder(val binding :MovieItemBinding, private val click: OnClickOnItem) :RecyclerView.ViewHolder(binding.root){
    fun onBind(item:Movie){
        binding.movie =item
        binding.linearLayout.setOnClickListener{

            click.onClickItem(item.id!!)
        }
    }

}

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater,parent,false)
        return MainViewHolder(binding,click)
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





