package com.example.villageevo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.ui.screens.HomeScreen
import com.example.villageevo.ui.screens.MapScreen
import com.example.villageevo.viewmodel.GameViewModel
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.SoldierViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    gameViewModel: GameViewModel,
    mapViewModel: MapViewModel,
    soldierViewModel: SoldierViewModel
)  {
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable ("home"){
            HomeScreen(navController, gameViewModel, mapViewModel, soldierViewModel)
        }
        composable (
            route = "map/{idMap}",
            arguments = listOf(navArgument("idMap"){type = NavType.IntType})
        ){
            backStackEntry ->
            val idMap = backStackEntry.arguments?.getInt("idMap") ?: 0
            MapScreen(
                idMap = idMap,
                navController = navController,
                gameViewModel = gameViewModel,
                mapViewModel = mapViewModel,
                soldierViewModel = soldierViewModel
            )
        }
    }
}