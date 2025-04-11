
# 🎬 Movie Bookmarks App

A modern Android application built using **Kotlin** and **Jetpack Compose** that allows users to **browse**, **bookmark**, and **view detailed information** about movies using the TMDB API. The app demonstrates the use of **Jetpack Compose**, **Hilt**, **Room**, and **MVVM architecture**.

---

## ✨ Features

- 🎞️ Browse and view trending/popular movies
- 🔖 Bookmark movies and manage them in a dedicated screen
- 🔍 View detailed information for each movie
- 🗂️ Clean MVVM architecture using ViewModel and Repository pattern
- 💉 Dependency Injection with Hilt
- 🗃️ Local storage using Room database
- 🌐 Network requests using Retrofit and OkHttp
- 📷 Images loaded with Glide
- 🎨 Modern UI built with Jetpack Compose
- 📱 Responsive and beautiful gradient background design

---

## 🧱 Tech Stack

| Layer             | Library / Framework               |
|------------------|-----------------------------------|
| Language          | Kotlin                            |
| UI                | Jetpack Compose                   |
| Architecture      | MVVM                              |
| DI                | Hilt                              |
| Networking        | Retrofit, OkHttp                  |
| Image Loading     | Glide                             |
| Local Storage     | Room Database                     |
| State Management  | Kotlin Flow                       |
| Navigation        | Jetpack Navigation-Compose        |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Kotlin 2.0+
- Gradle 8+
- Internet connection (for API requests)

---

### ⚙️ Setup Instructions

#### 1. **Clone the repository**
```bash
git clone https://github.com/your-username/movie-bookmarks-app.git
cd movie-bookmarks-app
```

#### 2. **Get a TMDB API Key**
- Sign up at [TMDB](https://www.themoviedb.org)
- Navigate to your account settings → API → Create an API key

#### 3. **Add API Key to `local.properties`**
Create a `local.properties` file (if it doesn’t already exist) in the root of your project and add:
```properties
TMDB_API_KEY=your_actual_tmdb_api_key
```

Then in your `build.gradle.kts` (app level), access it like:
```kotlin
val apiKey = project.properties["TMDB_API_KEY"] as String
```

#### 4. **Sync Gradle**
- Open the project in Android Studio
- Click **"Sync Now"** when prompted

---

### 🗃️ Room Database Setup

- Movie entity class stores movie ID, title, release date, and poster path
- DAO provides insert, delete, and query for bookmarks
- Database instance is provided using Hilt

```kotlin
@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
```

---

### 🌐 Retrofit & API Integration

- Base URL: `https://api.themoviedb.org/3/`
- Interceptor adds `api_key` to every request
- Repository handles data fetching and caching

```kotlin
@GET("movie/popular")
suspend fun getPopularMovies(): Response<MovieResponse>
```
---

## 🙋‍♀️ Author

**Ritika Mohan**  
Android Developer | Kotlin & Jetpack Compose Enthusiast

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```

