package com.quran.quran.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Surah::class, Ayah::class, FavoriteAyah::class], version = 3)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao

    companion object {
        @Volatile private var instance: QuranDatabase? = null

        fun getInstance(context: Context): QuranDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuranDatabase::class.java,
                    "quran_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}

