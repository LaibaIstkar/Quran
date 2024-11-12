package com.quran.quran.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quran.quran.data.Ayah
import com.quran.quran.data.Surah
import com.quran.quran.repository.QuranRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuranViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {
    val surahs: LiveData<List<Surah>> = repository.getAllSurahs()
    val favoriteAyahs: LiveData<List<Ayah>> = repository.getFavoriteAyahs()


    private val _ayahs = MutableLiveData<List<Ayah>>()
    val ayahs: LiveData<List<Ayah>> get() = _ayahs



    fun loadSurahs() = viewModelScope.launch {
        repository.fetchAndStoreSurahs()
        Log.d("QuranViewModel", "Loading Surahs")}

    fun loadAyahsForSurah(surahNumber: Int) = viewModelScope.launch {
        _ayahs.value = emptyList()

        repository.fetchAndStoreAyahs(surahNumber)

        val surahWithAyahs = repository.getAyahsForSurah(surahNumber)
        _ayahs.value = surahWithAyahs?.ayahs ?: emptyList()
    }

    fun toggleFavorite(ayah: Ayah) = viewModelScope.launch {
        if (isAyahFavorite(ayah.id)) {
            removeFavoriteAyah(ayah.id)
        } else {
            addFavoriteAyah(ayah.id)
        }
    }

    private fun addFavoriteAyah(ayahId: String) { viewModelScope.launch {
        repository.addFavoriteAyah(ayahId)
    }
    }

    private fun removeFavoriteAyah(ayahId: String) { viewModelScope.launch{
        repository.removeFavoriteAyah(ayahId)
    }
    }

    fun getFavoriteAyahs() {
         repository.getFavoriteAyahs()
    }

    suspend fun isAyahFavorite(ayahId: String): Boolean {
        return repository.isAyahFavorite(ayahId)
    }


}

