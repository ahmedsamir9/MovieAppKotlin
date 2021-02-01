package com.example.movieapp.utils

import android.view.View

fun View.isVisible(isVisible:Boolean){
    if (isVisible){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}