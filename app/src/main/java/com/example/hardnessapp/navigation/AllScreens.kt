package com.example.hardnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hardnessapp.screens.AddSampleScreen
import com.example.hardnessapp.screens.MainScreen
import com.example.hardnessapp.screens.SampleViewModel

sealed class AllScreens(val route: String) {

    object MainScreen :AllScreens("MainScreen")
    object AddScreen :AllScreens("AddScreen")
}

@Composable
fun AppNavHost(navigator: NavHostController, viewModel: SampleViewModel) {
    NavHost(navController = navigator, startDestination = AllScreens.MainScreen.route) {
        composable(AllScreens.MainScreen.route) { MainScreen(viewModel = viewModel, navigator = navigator) }
        composable(AllScreens.AddScreen.route) { AddSampleScreen(viewModel = viewModel, navigator = navigator) }
    }
}