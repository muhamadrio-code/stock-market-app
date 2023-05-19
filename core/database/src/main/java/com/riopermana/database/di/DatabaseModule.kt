package com.riopermana.database.di

import android.content.Context
import androidx.room.Room
import com.riopermana.database.StockDatabase
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
    fun providesStockDatabase(
        @ApplicationContext context: Context,
    ): StockDatabase =
        Room.databaseBuilder(
            context = context,
            name = "stock_db",
            klass = StockDatabase::class.java
        ).build()

}