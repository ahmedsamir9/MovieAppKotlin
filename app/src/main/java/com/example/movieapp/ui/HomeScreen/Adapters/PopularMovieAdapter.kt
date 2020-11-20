package com.example.movieapp.ui.HomeScreen.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.model.Movie

class PopularMovieAdapter(private var movies:List<Movie>?): RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {


    class PopularMovieViewHolder(private val popularMovieItemBinding: PopularMovieItemBinding) :RecyclerView.ViewHolder(popularMovieItemBinding.root){
        fun onBind(item:Movie){
            popularMovieItemBinding.imageUrl= item.posterPath
        }
    }
fun onChange(newList:List<Movie>){
    movies = newList
    notifyDataSetChanged()
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PopularMovieItemBinding.inflate(inflater,parent,false)
        return PopularMovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val movie = movies?.get(position)
       holder.onBind(movie!!)
    }
}