package com.riopermana.common.di

import android.content.Context
import com.riopermana.common.csv.CsvParser
import com.riopermana.common.csv.OpenCsvParser
import com.riopermana.common.helper.StringResHelper
import com.riopermana.common.helper.ResourcesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun providesCsvParser() : CsvParser = OpenCsvParser()

    @Provides
    fun providesResourceHelper(
        @ApplicationContext context: Context
    ) : ResourcesHelper<String> = StringResHelper(context)
}