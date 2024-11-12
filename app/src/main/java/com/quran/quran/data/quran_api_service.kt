package com.quran.quran.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface QuranApiService {
    @GET("surah")
    suspend fun getAllSurahs(): Response<SurahResponse>

    @GET("surah/{surahNumber}/en.asad")
    suspend fun getEnglishTranslation(@Path("surahNumber") surahNumber: Int): Response<AyahsDataResponse>

    @GET("surah/{surahNumber}")
    suspend fun getArabicAyahs(@Path("surahNumber") surahNumber: Int): Response<AyahsDataResponse>

    companion object {
        private const val BASE_URL = "https://api.alquran.cloud/v1/"

        fun create(): QuranApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuranApiService::class.java)
        }
    }
}

data class SurahResponse(val data: List<Surah>)

data class AyahsDataResponse(val data: SurahWithAyahsData)

data class SurahWithAyahsData(
    val ayahs: List<AyahData>
)

data class AyahData(
    val number: Int,
    val text: String,
    val numberInSurah: Int
)
