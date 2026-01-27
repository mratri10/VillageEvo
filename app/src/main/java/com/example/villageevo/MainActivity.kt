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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.db.AppDatabase
import com.example.villageevo.repository.MapRepository
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.repository.MapUserRepository
import com.example.villageevo.repository.SoldierRepository
import com.example.villageevo.ui.screens.HomeScreen
import com.example.villageevo.ui.screens.MainCityScreen
import com.example.villageevo.ui.screens.SelectScreen
import com.example.villageevo.ui.theme.VillageEvoTheme
import com.example.villageevo.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Sembunyikan Status Bar (Baterai, Signal) dan Navigation Bar (Tombol Home/Back)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.apply {
            // 1. Pilih tipe bar yang mau di-hide (systemBars menyembunyikan semuanya)
            hide(WindowInsetsCompat.Type.systemBars())

            // 2. Tentukan perilakunya: Muncul sebentar jika di-swipe, lalu hide lagi
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val database = AppDatabase.getDatabase(this)
        val mapUserDao = database.mapUserDao()
        val repositoryUser = MapUserRepository(mapUserDao)

        val repository = MapRepository(this)
        val repositorySoldier = SoldierRepository(this)

        val viewModel = GameViewModel(repository)
        val mapUserModel = MapViewModel(repositoryUser)
        val soldierViewModel = SoldierViewModel(repositorySoldier)


        mapUserModel.dataMapUser()
        setContent {
            VillageEvoTheme(dynamicColor = false) {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    val screen by viewModel.currentScreen.collectAsState()
                    val isUserExist by mapUserModel.isUserExist.collectAsStateWithLifecycle()

                    when (screen) {
                        Screen.HOME ->if(isUserExist)
                            SelectScreen(mapUserModel)
                        else
                            HomeScreen(viewModel, mapUserModel, soldierViewModel)
                        Screen.CITY -> MainCityScreen(viewModel)
                    }
                }
            }
        }
    }
}
