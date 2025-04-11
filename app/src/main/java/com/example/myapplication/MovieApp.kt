package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.bookmark.BookmarksScreen
import com.example.myapplication.ui.screens.details.MovieDetailsScreen
import com.example.myapplication.ui.screens.search.SearchScreen
import com.example.themoviedb.ui.screens.home.HomeScreen


sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    data object Home : Screen("home", Icons.Filled.Home, "Home")
    data object Search : Screen("search", Icons.Filled.Search, "Search")
    data object Bookmark : Screen("bookmark", Icons.Filled.Favorite, "Bookmark")
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Search, Screen.Bookmark)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFAFDBF5), // Background color
                tonalElevation = 4.dp // Optional: elevation (shadow)
            ) {
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White, // Set background to white
                            selectedIconColor = Color.Black, // icon color when selected
                            unselectedIconColor = Color.DarkGray, // icon color when not selected
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.DarkGray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(
                route = "details/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailsScreen(movieId, navController)
            }
            composable(Screen.Search.route) { SearchScreen(navController) }
            composable(Screen.Bookmark.route) { BookmarksScreen(navController) }
        }
    }
}
