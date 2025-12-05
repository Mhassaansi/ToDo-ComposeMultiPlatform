package com.ubl.todolist.presentation.navigation


sealed class NavRoutes(val route: String) {
    object SplashScreen : NavRoutes("splash")
    object HomeScreen : NavRoutes("home")
    object AddTaskScreen : NavRoutes("add_task")
    object TaskDetailScreen : NavRoutes("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    object SettingsScreen : NavRoutes("settings")
    object ProfileScreen : NavRoutes("profile")
}
