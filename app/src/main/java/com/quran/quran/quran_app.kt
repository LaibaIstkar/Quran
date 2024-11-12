package com.quran.quran

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quran.quran.ui.quran.FavoriteAyahsPage
import com.quran.quran.ui.quran.SurahDetailScreen
import com.quran.quran.ui.quran.SurahListScreen
import com.quran.quran.viewmodel.QuranViewModel

@Composable
fun QuranApp(viewModel: QuranViewModel, onToggleTheme: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "surahList") {
        composable("surahList") {
            SurahListScreen(
                viewModel = viewModel,
                onSurahClick = { surahNumber ->
                    navController.navigate("surahDetail/$surahNumber")
                },
                onToggleTheme = onToggleTheme,
                onFavoritesClick = {
                    navController.navigate("favoriteAyahs")
                }
            )
        }
        composable("surahDetail/{surahNumber}") { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getString("surahNumber")?.toIntOrNull() ?: return@composable
            SurahDetailScreen(
                viewModel = viewModel,
                surahNumber = surahNumber,
                onBack = { navController.navigate("surahList") }
            )
        }
        composable("favoriteAyahs") {
            FavoriteAyahsPage(viewModel, onBack = { navController.navigate("surahList") })
        }
    }
}
