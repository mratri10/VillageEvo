package com.example.villageevo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villageevo.ui.screens.main.VillageScreen
import com.example.villageevo.ui.theme.VillageEvoTheme
import com.example.villageevo.viewmodel.VillageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VillageEvoTheme {
                val vm: VillageViewModel = viewModel()
                VillageScreen(vm)
            }
        }
    }
}
