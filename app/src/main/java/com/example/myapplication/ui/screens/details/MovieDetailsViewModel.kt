package com.example.themoviedb.ui.screens.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = repository.getMovieDetails(apiKey, movieId)
                _movieDetails.value = movie

                // Collect the bookmark state from Flow
                repository.isBookmarked(movie.id).collect { isBookmarked ->
                    _isBookmarked.value = isBookmarked
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun toggleBookmark(movie: Movie) {
        viewModelScope.launch {
            val currentlyBookmarked = _isBookmarked.value
            if (currentlyBookmarked) {
                repository.removeBookmark(movie) // Stub method
            } else {
                repository.addBookmark(movie) // Stub method
            }
            _isBookmarked.value = !currentlyBookmarked
        }
    }
}

