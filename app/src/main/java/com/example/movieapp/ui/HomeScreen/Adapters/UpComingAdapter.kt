package com.example.movieapp.ui.HomeScreen.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.UpComingMovies
import com.example.movieapp.utils.Constant
import com.example.movieapp.utils.OnClickOnItem

class UpComingAdapter(private val click: OnClickOnItem):PagingDataAdapter<UpComingMovies, UpComingAdapter.UpComingViewHolder>(
    moviesDiffUtils
) {



class UpComingViewHolder(val binding :MovieItemBinding,private val click: OnClickOnItem) :RecyclerView.ViewHolder(binding.root){
    fun onBind(item:UpComingMovies){
        val moive:Movie? =Movie(item.backdropPath,item.id,item.originalTitle,item.posterPath,item.title,
            Constant.UP_COMING_CATEGORY,item.voteAverage)

        binding.movie =moive
         binding.linearLayout.setOnClickListener {
             click.onClickItem(moive?.id!!)
         }
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
        return UpComingViewHolder(binding,click)
    }
    companion object{
        val moviesDiffUtils = object :DiffUtil.ItemCallback<UpComingMovies>(){
            override fun areItemsTheSame(
                oldItem: UpComingMovies,
                newItem: UpComingMovies
            ): Boolean {
                return  oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: UpComingMovies,
                newItem: UpComingMovies
            ): Boolean {
                return  oldItem == newItem

        }

    }

}
}





