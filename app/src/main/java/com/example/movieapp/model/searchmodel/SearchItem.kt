package com.example.movieapp.model.searchmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchItem (
@PrimaryKey
val id :Int,
@ColumnInfo
val title:String,
@ColumnInfo
val keyword:String,
@ColumnInfo
val posterPath:String,
@ColumnInfo
val mediaType:String
)