package com.example.movieapp.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorItemDecorator (val spacing:Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        outRect.left = spacing/2
        outRect.bottom = spacing/2
        outRect.top= spacing/2
        outRect.right= spacing/2
        if (position >0){
            outRect.left =spacing
        }
    }
}