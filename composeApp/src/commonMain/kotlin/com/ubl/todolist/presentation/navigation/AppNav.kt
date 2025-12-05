package com.ubl.todolist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.presentation.screens.ProfileScreen
import com.ubl.todolist.presentation.screens.SettingsScreen
import com.ubl.todolist.presentation.screens.SplashScreen
import com.ubl.todolist.presentation.screens.TaskDetailScreen
import com.ubl.todolist.presentation.screens.addTask.AddTaskScreen
import com.ubl.todolist.presentation.screens.home.HomeScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.SplashScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.SplashScreen.route) {
            SplashScreen(
                onTimeOut = {
                    navController.navigate(NavRoutes.HomeScreen.route) {
                        popUpTo(NavRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoutes.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        // Home
        composable(NavRoutes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        // Add Task
        composable(NavRoutes.AddTaskScreen.route) {
            AddTaskScreen(navController = navController)
        }

        // Task Detail
        composable(NavRoutes.TaskDetailScreen.route) { backStackEntry ->
            val task = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Task>("task")
            TaskDetailScreen(navController = navController, task = task)

        }

        // Settings
        composable(NavRoutes.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}
