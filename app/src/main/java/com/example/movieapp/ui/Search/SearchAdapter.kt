package com.example.movieapp.ui.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.SearchItemBinding
import com.example.movieapp.model.PlayingMovies
import com.example.movieapp.model.searchmodel.SearchItem
import com.example.movieapp.ui.HomeScreen.Adapters.PlayingNowAdapter

class SearchAdapter(val interactor :OnSelectItem) : PagingDataAdapter<SearchItem, SearchAdapter.SearchViewHolder>(
    SearchItemDiffCallback()
) {
    class SearchViewHolder(val binder: SearchItemBinding,val interactor: OnSelectItem) : RecyclerView.ViewHolder(binder.root) {
        fun onBind(item: SearchItem) {
            binder.item = item
            binder.root.setOnClickListener {
                interactor.onClickItem(item)
            }
        }
    }

    private class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = SearchItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binder,interactor)
    }
    interface OnSelectItem{
        fun onClickItem(item:SearchItem)
    }
}