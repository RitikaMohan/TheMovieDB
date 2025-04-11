package com.example.myapplication.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val backdrop_path: String?,
    val release_date: String,
    val vote_average: Float,
    val runtime: Int
)

