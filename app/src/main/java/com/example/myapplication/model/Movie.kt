package com.example.myapplication.model


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String?,
    val category: String, // e.g., "trending", "now_playing"
    val release_date: String
)