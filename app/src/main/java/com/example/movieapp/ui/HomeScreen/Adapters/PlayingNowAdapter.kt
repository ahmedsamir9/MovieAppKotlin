package com.example.movieapp.ui.HomeScreen.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.PlayingMovies
import com.example.movieapp.model.UpComingMovies
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.OnClickOnItem

class PlayingNowAdapter(private val click: OnClickOnItem):PagingDataAdapter<PlayingMovies, PlayingNowAdapter.PlayingViewHolder>(
    PlayDiffUtils
) {



class PlayingViewHolder(val binding :MovieItemBinding,private val click: OnClickOnItem) :RecyclerView.ViewHolder(binding.root){
    fun onBind(item:PlayingMovies){
        val moive:Movie? =Movie(item.backdropPath,item.id,item.originalTitle,item.posterPath,item.title,
            Constant.UP_COMING_CATEGORY,item.voteAverage)

        binding.movie =moive
        binding.linearLayout.setOnClickListener {
            click.onClickItem(moive?.id!!)
        }
    }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater,parent,false)
        return PlayingViewHolder(binding,click)
    }
    companion object{
        val PlayDiffUtils = object :DiffUtil.ItemCallback<PlayingMovies>(){
            override fun areItemsTheSame(oldItem: PlayingMovies, newItem: PlayingMovies): Boolean {
                return  oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: PlayingMovies,
                newItem: PlayingMovies
            ): Boolean {
                return  oldItem == newItem
            }

        }

}

    override fun onBindViewHolder(holder: PlayingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }
}





