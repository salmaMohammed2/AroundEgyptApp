package com.example.aroundegypt.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aroundegypt.presentation.screens.ExperienceDetailsScreen
import com.example.aroundegypt.presentation.screens.MainExperienceScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: androidx.compose.ui.Modifier) {

    NavHost(navController = navController, startDestination = "mainExperienceScreen") {
        composable("mainExperienceScreen") { MainExperienceScreen(navController, modifier) }
        composable(
            route = "experienceDetailsScreen/{experienceId}/{experienceType}",
            arguments = listOf(
                navArgument("experienceId") {
                    type = NavType.StringType
                },
                navArgument("experienceType") {
                    type = NavType.IntType
                }
            ),
        ) { backStackEntry ->
            val experienceId = backStackEntry.arguments?.getString("experienceId") ?: ""
            val experienceType = backStackEntry.arguments?.getInt("experienceType") ?: 0
            ExperienceDetailsScreen(experienceId = experienceId, experienceType = experienceType)

        }
    }
}