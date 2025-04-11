package com.example.themoviedb.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Movie
import com.example.myapplication.ui.screens.home.HomeViewModel
import com.example.myapplication.utils.TMDB_IMAGE_BASE_URL
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val trending by viewModel.trendingMovies.collectAsState()
    val nowPlaying by viewModel.nowPlayingMovies.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Trending", style = MaterialTheme.typography.titleLarge)
            MovieRow(trending, viewModel) { movie ->
                navController.navigate("details/${movie.id}")
            }
        }
        item {
            Text("Now Playing", style = MaterialTheme.typography.titleLarge)
            MovieRow(nowPlaying, viewModel) { movie ->
                navController.navigate("details/${movie.id}")
            }
        }
    }
}


@Composable
fun MovieRow(
    movies: List<Movie>,
    viewModel: HomeViewModel,
    onClick: (Movie) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(movies) { movie ->
            val isBookmarked by viewModel.isBookmarked(movie.id).collectAsState()

            Column(
                modifier = Modifier
                    .width(140.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onClick(movie) }
                ) {
                    GlideImage(
                        imageModel = { TMDB_IMAGE_BASE_URL + (movie.poster_path ?: "") },
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                    )
                    IconButton(
                        onClick = { viewModel.toggleBookmark(movie) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
