package com.riopermana.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.riopermana.stockmarket.ui.theme.StockMarketTheme
import com.riopermana.ui.StockMarketApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockMarketTheme {
                StockMarketApp()
            }
        }
    }
}