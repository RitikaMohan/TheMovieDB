package com.example.myapplication.ui.screens.details

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.model.Movie
import com.example.myapplication.utils.TMDB_IMAGE_BASE_URL
import com.example.themoviedb.ui.screens.details.MovieDetailsViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MovieDetailsScreen(
    movieId: Int,
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val movieDetails by viewModel.movieDetails.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    Scaffold(
    ) { paddingValues ->

        movieDetails?.let { movie ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // 👇 Poster
                GlideImage(
                    imageModel = { TMDB_IMAGE_BASE_URL + (movie.backdrop_path ?: "") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))


                // 👇 Row for Bookmark and Share below TopBar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Title
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        val bookmarkModel = Movie(
                            id = movie.id,
                            title = movie.title,
                            release_date = movie.release_date ?: "",
                            poster_path = movie.backdrop_path ?: "",
                            overview = movie.overview ?: "",
                            category = "Bookmark"
                        )
                        viewModel.toggleBookmark(bookmarkModel)
                    }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = Color.Black
                        )
                    }

                    IconButton(onClick = {
                        val shareText = "Check out this movie: ${movie.title}\nhttps://www.themoviedb.org/movie/${movie.id}"
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Rating and Runtime
                Text("Rating: ${movie.vote_average}")
                Text("Runtime: ${movie.runtime} mins")

                Spacer(modifier = Modifier.height(12.dp))

                // Overview
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
