package com.example.villageevo.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.villageevo.domain.city.CityState

@Composable
fun EraProgressView(city: CityState){
    Text("Current Era: ${city.currentEra}")
}