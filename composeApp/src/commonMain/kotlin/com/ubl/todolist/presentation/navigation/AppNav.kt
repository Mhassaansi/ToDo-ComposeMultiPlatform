package com.ubl.todolist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubl.todolist.extension.Constant.EDIT_NOTES_ARGUMENT
import com.ubl.todolist.extension.Constant.IS_EDITABLE
import com.ubl.todolist.extension.Constant.TASK_ID
import com.ubl.todolist.presentation.screens.SplashScreen
import com.ubl.todolist.presentation.screens.addTask.AddTaskScreen
import com.ubl.todolist.presentation.screens.home.HomeScreen
import com.ubl.todolist.presentation.screens.taskDetail.TaskDetailScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.SplashScreen.route
) {
    NavHost(
        navController = navController, startDestination = startDestination
    )
    {
        composable(NavRoutes.SplashScreen.route) {
            SplashScreen(
                onTimeOut = {
                    navController.navigate(NavRoutes.HomeScreen.route) {
                        popUpTo(NavRoutes.SplashScreen.route) { inclusive = true }
                    }
                })
        }
        composable(NavRoutes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(NavRoutes.AddTaskScreen.route) {
            val isEditable =
                navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>(IS_EDITABLE)
            val taskID = navController.previousBackStackEntry?.savedStateHandle?.get<Int>(TASK_ID)
            AddTaskScreen(navController = navController, isEditable = isEditable, taskID)
        }
        composable(NavRoutes.TaskDetailScreen.route) { backStackEntry ->
            val noteId =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(EDIT_NOTES_ARGUMENT)
            TaskDetailScreen(navController = navController, taskId = noteId)

        }
    }
}
