package com.example.movieapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.utils.Constant.IMAGE_BASE_URL

@BindingAdapter("setImageByGlide")
fun setImageByGlide(imageView: ImageView,path:String?){
        val imagePath = IMAGE_BASE_URL+path
        Glide.with(imageView.context)
            .load(imagePath)
            .error(R.drawable.movie)
            .placeholder(R.drawable.loading_animation)
            .into(imageView);
}