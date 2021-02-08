package com.example.movieapp.ui.Details.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.SimilarMovieItemBinding
import com.example.movieapp.model.SimilarMovie

class SimilarMoviesAdapter(private val interaction: Interaction? = null) :
   PagingDataAdapter<SimilarMovie, SimilarMoviesAdapter.SimilarMovieViewHolder>(SimilarMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val binder = SimilarMovieItemBinding.inflate(inflater,parent,false)
        return SimilarMovieViewHolder(binder,interaction)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SimilarMovieViewHolder(
        private val binder: SimilarMovieItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binder.root), OnClickListener {
        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            getItem(adapterPosition)?.let { interaction?.onItemSelected(adapterPosition, it) }
        }
init {
    binder.root.setOnClickListener(this)
}
        fun bind(item: SimilarMovie) {
            binder.movie =item
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: SimilarMovie)
    }

    private class SimilarMovieDiffCallback : DiffUtil.ItemCallback<SimilarMovie>() {
        override fun areItemsTheSame(
            oldItem: SimilarMovie,
            newItem: SimilarMovie
        ): Boolean {
           return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SimilarMovie,
            newItem: SimilarMovie
        ): Boolean {
            return oldItem == newItem
        }
    }
}