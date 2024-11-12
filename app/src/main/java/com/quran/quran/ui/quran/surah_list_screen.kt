package com.quran.quran.ui.quran

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quran.quran.data.Surah
import com.quran.quran.ui.theme.ThemeToggler
import com.quran.quran.viewmodel.QuranViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahListScreen(viewModel: QuranViewModel, onSurahClick: (Int) -> Unit, onToggleTheme: () -> Unit, onFavoritesClick: () -> Unit) {
    val surahs by viewModel.surahs.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadSurahs()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quran", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    ThemeToggler(darkTheme = isSystemInDarkTheme(), onToggleTheme = onToggleTheme)
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            tint = Color.Red // Set the heart icon color to red
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            itemsIndexed(surahs) { index, surah ->
                SurahItem(surah, index, onSurahClick)
            }
        }
    }
}


@Composable
fun SurahItem(surah: Surah, index: Int, onSurahClick: (Int) -> Unit) {
    val backgroundColor = if (index % 2 == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSurahClick(surah.number) }
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background( if (index % 2 == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = surah.number.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = surah.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = surah.englishNameTranslation,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Ayahs: ${surah.numberOfAyahs}, ${surah.revelationType}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}
