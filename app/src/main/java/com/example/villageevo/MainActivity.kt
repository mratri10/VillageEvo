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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.db.AppDatabase
import com.example.villageevo.repository.MapRepository
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.repository.MapUserRepository
import com.example.villageevo.ui.screens.HomeScreen
import com.example.villageevo.ui.screens.MainCityScreen
import com.example.villageevo.ui.screens.SelectScreen
import com.example.villageevo.ui.theme.VillageEvoTheme
import com.example.villageevo.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val mapUserDao = database.mapUserDao()
        val repositoryUser = MapUserRepository(mapUserDao)

        val repository = MapRepository(this)
        val viewModel = GameViewModel(repository)

        val mapUserModel = MapViewModel(repositoryUser)

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
                            HomeScreen(viewModel, mapUserModel)
                        Screen.CITY -> MainCityScreen(viewModel)
                    }
                }
            }
        }
    }
}
