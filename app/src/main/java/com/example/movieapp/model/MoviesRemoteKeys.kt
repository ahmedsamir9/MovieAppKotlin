package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesRemoteKeys (
    @PrimaryKey(autoGenerate = true)
    val pk : Int,
    val afterIndex:Int?,
    val beforeIndex:Int?,
    val category: String
)

