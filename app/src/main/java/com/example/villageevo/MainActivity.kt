package com.example.villageevo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.villageevo.domain.db.AppDatabase
import com.example.villageevo.ui.screens.HomeScreen
import com.example.villageevo.ui.screens.MainCityScreen
import com.example.villageevo.ui.theme.VillageEvoTheme
import com.example.villageevo.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = database.mapUserDao()
        val factory = GameViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]
        enableEdgeToEdge()
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        setContent {
            VillageEvoTheme(dynamicColor = false) {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    val screen by viewModel.currentScreen.collectAsState()
                    when (screen) {
                        Screen.HOME -> HomeScreen(viewModel)
                        Screen.CITY -> MainCityScreen(viewModel)
                    }
                }
            }
        }
    }
}
