package com.quran.quran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.quran.quran.viewmodel.QuranViewModel
import androidx.compose.runtime.mutableStateOf
import com.quran.quran.ui.theme.QuranTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: QuranViewModel

    private val isDarkTheme = mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuranApplication()
            QuranTheme(darkTheme = isDarkTheme.value) {
                QuranApp(viewModel) {
                    isDarkTheme.value = !isDarkTheme.value
                }
            }
        }

    }
}


