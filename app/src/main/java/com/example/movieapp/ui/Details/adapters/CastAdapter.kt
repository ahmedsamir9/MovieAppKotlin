package com.example.movieapp.ui.Details.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieActorItemBinding
import com.example.movieapp.model.Cast

class CastAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Cast, CastAdapter.CastViewHolder>(CastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = MovieActorItemBinding.inflate(inflater,parent,false)
        return CastViewHolder(binder,interaction)
    }

    override fun onBindViewHolder(holder:CastViewHolder , position: Int) {
        holder.bind(getItem(position))
    }

    inner class CastViewHolder(
     private val binding: MovieActorItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cast) {
        binding.actorData =item
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Cast)
    }

    private class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(
            oldItem: Cast,
            newItem: Cast
        ): Boolean {
            return oldItem.castId == newItem.castId
        }

        override fun areContentsTheSame(
            oldItem: Cast,
            newItem: Cast
        ): Boolean {
            return oldItem == newItem
        }
    }
}