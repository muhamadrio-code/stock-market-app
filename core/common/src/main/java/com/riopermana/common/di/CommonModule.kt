package com.riopermana.common.di

import com.riopermana.common.csv.CsvParser
import com.riopermana.common.csv.OpenCsvParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun providesCsvParser() : CsvParser = OpenCsvParser()
}