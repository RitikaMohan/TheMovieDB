package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie")
    fun getAllSavedMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: Int): Movie?

    @Query("SELECT * FROM movie WHERE category = :category")
    fun getMoviesByCategory(category: String): List<Movie>

    @Query("SELECT * FROM movie WHERE category = 'bookmarked'")
    fun getAllBookmarked(): Flow<List<Movie>>

    @Query("SELECT EXISTS(SELECT 1 FROM movie WHERE id = :movieId AND category = 'bookmarked')")
    fun isBookmarked(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(movie: Movie)

    @Delete
    suspend fun deleteBookmark(movie: Movie)
}
