package com.quran.quran.ui.quran

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quran.quran.data.Ayah
import com.quran.quran.viewmodel.QuranViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.quran.quran.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(
    viewModel: QuranViewModel,
    surahNumber: Int,
    onBack: () -> Unit
) {
    val ayahs by viewModel.ayahs.observeAsState(emptyList())
    var isBookView by remember { mutableStateOf(false) }

    LaunchedEffect(surahNumber) {
        viewModel.loadAyahsForSurah(surahNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isBookView = !isBookView }) {
                        Icon(
                            imageVector = if (isBookView) Icons.Default.List else Icons.Default.DateRange,
                            contentDescription = if (isBookView) "Switch to List View" else "Switch to Book View"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            if (isBookView) {
                item {
                    AyahBookViewItem(ayahs)
                }
            } else {
                itemsIndexed(ayahs) { _, ayah ->
                    val isFavorite = remember { mutableStateOf(false) }

                    LaunchedEffect(ayah.id) {
                        isFavorite.value = viewModel.isAyahFavorite(ayah.id)
                    }

                    AyahListViewItem(
                        ayah = ayah,
                        isFavorite = isFavorite,
                        onFavoriteClick = {
                            viewModel.toggleFavorite(it)
                            isFavorite.value = !isFavorite.value
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AyahListViewItem(
    ayah: Ayah,
    isFavorite: MutableState<Boolean>,
    onFavoriteClick: (Ayah) -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.secondary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ayah.numberInSurah.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        IconButton(onClick = { onFavoriteClick(ayah) }) {
            Icon(
                imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite.value) Color.Red else MaterialTheme.colorScheme.onBackground
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(15.dp)
    ) {
        Text(
            text = ayah.arabicText,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = ayah.translationText,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun AyahBookViewItem(ayahs: List<Ayah>) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\uFDFD",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        contentAlignment = Alignment.Center
    ) {

        Text(
            buildAnnotatedString {
                ayahs.forEach { ayah ->
                    // Arabic text for Ayah
                    withStyle(
                        style = SpanStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(
                                Font(R.font.amiri_regular, FontWeight.Normal)
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    ) {
                        append(ayah.arabicText.trim())
                    }

                    // Space between Arabic text and Ayah number
                    append(" ")

                    // Ayah number in a circular shape
                    withStyle(
                        style = SpanStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(
                                Font(R.font.varelaround_regular, FontWeight.Normal)
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    ) {
                        append("﹙ ${ayah.numberInSurah} )")
                    }

                    append("  ")
                }
            },
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
