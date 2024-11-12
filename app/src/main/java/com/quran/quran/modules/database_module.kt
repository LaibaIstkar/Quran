package com.quran.quran.modules

import android.content.Context
import com.quran.quran.data.QuranDao
import com.quran.quran.data.QuranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideQuranDatabase(@ApplicationContext context: Context): QuranDatabase {
        return QuranDatabase.getInstance(context)
    }

    @Provides
    fun provideQuranDao(quranDatabase: QuranDatabase): QuranDao {
        return quranDatabase.quranDao()
    }
}
