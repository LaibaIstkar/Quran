package com.quran.quran.repository

import androidx.lifecycle.LiveData
import com.quran.quran.data.Ayah
import com.quran.quran.data.FavoriteAyah
import com.quran.quran.data.QuranApiService
import com.quran.quran.data.QuranDao
import com.quran.quran.data.SurahWithAyahs
import javax.inject.Inject

class QuranRepository
    @Inject constructor(
    private val apiService: QuranApiService,
    private val dao: QuranDao
) {
    suspend fun fetchAndStoreSurahs() {
        if (dao.getAllSurahs().value?.isNotEmpty() == true) {
            return
        }

        val response = apiService.getAllSurahs()
        if (response.isSuccessful) {
            response.body()?.data?.let { surahs ->
                dao.insertSurahs(surahs)
            }
        }
    }

    suspend fun fetchAndStoreAyahs(surahNumber: Int) {
        val existingAyahs = dao.getSurahWithAyahs(surahNumber)?.ayahs
        if (!existingAyahs.isNullOrEmpty()) {
            return
        }

        val arabicResponse = apiService.getArabicAyahs(surahNumber)
        val englishResponse = apiService.getEnglishTranslation(surahNumber)

        if (arabicResponse.isSuccessful && englishResponse.isSuccessful) {
            val arabicAyahs = arabicResponse.body()?.data?.ayahs ?: emptyList()
            val englishAyahs = englishResponse.body()?.data?.ayahs ?: emptyList()

            val ayahs = arabicAyahs.zip(englishAyahs) { arabic, english ->
                val modifiedText = if (arabic.numberInSurah == 1 && arabic.text.contains("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیم")) {
                    arabic.text.replace("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ", "").trim()
                } else {
                    arabic.text
                }

                Ayah(
                    id = "${surahNumber}_${arabic.numberInSurah}",
                    surahId = surahNumber,
                    numberInSurah = arabic.numberInSurah,
                    arabicText = modifiedText,
                    translationText = english.text
                )
            }

            dao.insertAyahs(ayahs)
        }
    }



    fun getAllSurahs() = dao.getAllSurahs()
    suspend fun getAyahsForSurah(surahNumber: Int): SurahWithAyahs? {
        return dao.getSurahWithAyahs(surahNumber)
    }


    suspend fun addFavoriteAyah(ayahId: String) {
        dao.insertFavoriteAyah(FavoriteAyah(ayahId))
    }

    suspend fun removeFavoriteAyah(ayahId: String) {
        dao.deleteFavoriteAyah(ayahId)
    }

    fun getFavoriteAyahs(): LiveData<List<Ayah>> {
        return dao.getFavoriteAyahs()
    }

    suspend fun isAyahFavorite(ayahId: String): Boolean {
        return dao.isAyahFavorite(ayahId)
    }
}
