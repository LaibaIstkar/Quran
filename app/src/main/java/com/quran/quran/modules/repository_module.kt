package com.quran.quran.modules

import com.quran.quran.data.QuranApiService
import com.quran.quran.data.QuranDao
import com.quran.quran.repository.QuranRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideQuranRepository(
        apiService: QuranApiService,
        dao: QuranDao
    ): QuranRepository {
        return QuranRepository(apiService, dao)
    }
}
