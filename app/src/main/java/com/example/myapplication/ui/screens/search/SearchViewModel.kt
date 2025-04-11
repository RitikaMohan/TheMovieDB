package com.example.myapplication.ui.screens.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myapplication.BuildConfig
import com.example.myapplication.model.Movie

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = repository.searchMovies(apiKey, query).results
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}