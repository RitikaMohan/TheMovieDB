package com.example.myapplication.data.repository

import com.example.myapplication.data.api.TMDBApiService
import com.example.myapplication.data.db.MovieDAO
import com.example.myapplication.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: TMDBApiService,
    private val movieDao: MovieDAO
) {
    suspend fun searchMovies(apiKey: String, query: String) = api.searchMovies(apiKey, query)
    suspend fun getMovieDetails(apiKey: String, movieId: Int) = api.getMovieDetails(movieId, apiKey)

    suspend fun saveMovie(movie: Movie) = movieDao.insertMovie(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)
//    fun getBookmarkedMovies() = movieDao.getAllSavedMovies()

    fun getBookmarkedMovies(): Flow<List<Movie>> = movieDao.getAllBookmarked()

    fun isBookmarked(movieId: Int): Flow<Boolean> = movieDao.isBookmarked(movieId)

    suspend fun addBookmark(movie: Movie) {
        val bookmarkedMovie = movie.copy(category = "bookmarked")
        movieDao.insertBookmark(bookmarkedMovie)
    }

    suspend fun removeBookmark(movie: Movie) = movieDao.deleteBookmark(movie)

    suspend fun getTrendingMovies(apiKey: String): List<Movie> {
        return try {
            val response = api.getTrendingMovies(apiKey)
            val categorized = response.results.map { it.copy(category = "trending") }
            movieDao.insertMovies(categorized)
            categorized
        } catch (e: Exception) {
            movieDao.getMoviesByCategory("trending")
        }
    }

    suspend fun getNowPlayingMovies(apiKey: String): List<Movie> {
        return try {
            val response = api.getNowPlayingMovies(apiKey)
            val categorized = response.results.map { it.copy(category = "now_playing") }
            movieDao.insertMovies(categorized)
            categorized
        } catch (e: Exception) {
            movieDao.getMoviesByCategory("now_playing")
        }
    }

    suspend fun getCachedMoviesByCategory(category: String): List<Movie> {
        return movieDao.getMoviesByCategory(category)
    }
}