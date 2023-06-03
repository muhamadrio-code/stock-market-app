package com.riopermana.stockmarket

import android.app.Application
import com.riopermana.sync.initializer.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StockMarketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Sync.initializeSync(this)
    }
}