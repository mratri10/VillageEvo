package com.example.villageevo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.villageevo.ui.screens.MainCityScreen
import com.example.villageevo.viewmodel.*

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screen by viewModel.currentScreen.collectAsState()
            when(screen){
                Screen.CITY -> MainCityScreen(viewModel)
                Screen.MARKET -> MainCityScreen(viewModel)
                Screen.WORKERS -> MainCityScreen(viewModel)
            }
        }
    }
}
