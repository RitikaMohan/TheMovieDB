package com.example.myapplication.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MovieRepository
import com.example.myapplication.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myapplication.BuildConfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlayingMovies

    init {
        fetchTrendingMovies()
        fetchNowPlayingMovies()
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                val movies = repository.getTrendingMovies(apiKey)
                _trendingMovies.value = movies.map { it.copy(category = "trending") }
            } catch (e: Exception) {
                _trendingMovies.value = repository.getCachedMoviesByCategory("trending")
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val movies = repository.getNowPlayingMovies(apiKey)
                _nowPlayingMovies.value = movies.map { it.copy(category = "now_playing") }
            } catch (e: Exception) {
                _nowPlayingMovies.value = repository.getCachedMoviesByCategory("now_playing")
            }
        }
    }

    fun isBookmarked(movieId: Int): StateFlow<Boolean> {
        return repository.isBookmarked(movieId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    }

    fun toggleBookmark(movie: Movie) {
        viewModelScope.launch {
            val bookmarked = repository.isBookmarked(movie.id).first()
            if (bookmarked) {
                Log.d("Bookmark", "Removing bookmark for: ${movie.title}")
                repository.removeBookmark(movie)
            } else {
                Log.d("Bookmark", "Adding bookmark for: ${movie.title}")
                repository.addBookmark(movie)
            }
        }
    }
}
