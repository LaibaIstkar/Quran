package com.quran.quran.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction

@Entity(tableName = "surah")
data class Surah(
    @PrimaryKey val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
)



@Entity(tableName = "ayah")
data class Ayah(
    @PrimaryKey val id: String,
    val surahId: Int,
    val numberInSurah: Int,
    val arabicText: String,
    val translationText: String
)



data class SurahWithAyahs(
    @Embedded val surah: Surah,
    @Relation(
        parentColumn = "number",
        entityColumn = "surahId"
    )
    val ayahs: List<Ayah>
)

@Entity(tableName = "favorite_ayah")
data class FavoriteAyah(
    @PrimaryKey val ayahId: String
)


@Dao
interface QuranDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurahs(surahs: List<Surah>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyahs(ayahs: List<Ayah>)

    @Query("SELECT * FROM surah")
    fun getAllSurahs(): LiveData<List<Surah>>

    @Transaction
    @Query("SELECT * FROM surah WHERE number = :surahNumber")
    suspend fun getSurahWithAyahs(surahNumber: Int): SurahWithAyahs?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteAyah(favoriteAyah: FavoriteAyah)

    @Query("DELETE FROM favorite_ayah WHERE ayahId = :ayahId")
    suspend fun deleteFavoriteAyah(ayahId: String)

    @Query("SELECT * FROM ayah INNER JOIN favorite_ayah ON ayah.id = favorite_ayah.ayahId")
    fun getFavoriteAyahs(): LiveData<List<Ayah>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_ayah WHERE ayahId = :ayahId)")
    suspend fun isAyahFavorite(ayahId: String): Boolean
}

