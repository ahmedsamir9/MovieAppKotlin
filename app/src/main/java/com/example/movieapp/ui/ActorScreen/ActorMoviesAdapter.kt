package com.example.movieapp.ui.ActorScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.ActorMovieItemBinding
import com.example.movieapp.databinding.MovieActorItemBinding
import com.example.movieapp.model.ActorMovies

class ActorMoviesAdapter(private val interaction: Interaction? = null) :
    ListAdapter<ActorMovies, ActorMoviesAdapter.ActorMovieViewHolder>(ActorMoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val binder = ActorMovieItemBinding.inflate(inflater,parent,false)
        return ActorMovieViewHolder(binder,interaction)
    }

    override fun onBindViewHolder(holder: ActorMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ActorMovieViewHolder(
        val binder: ActorMovieItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binder.root), OnClickListener {

        init {
            binder.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            interaction?.onItemSelected(getItem(adapterPosition))
        }

        fun bind(item: ActorMovies) {
            binder.movie =item
        }
    }

    interface Interaction {

        fun onItemSelected(item: ActorMovies)
    }

    private class ActorMoviesDiffCallback : DiffUtil.ItemCallback<ActorMovies>() {
        override fun areItemsTheSame(
            oldItem: ActorMovies,
            newItem: ActorMovies
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActorMovies,
            newItem: ActorMovies
        ): Boolean {
        return  oldItem == newItem
        }
    }
}