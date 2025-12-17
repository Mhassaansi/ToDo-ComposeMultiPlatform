package com.ubl.todolist.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ubl.todolist.presentation.navigation.NavRoutes

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavRoutes.HomeScreen)
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    when (screen) {
                        NavRoutes.HomeScreen -> Icon(Icons.Default.Home, "Home")
                        else -> {}
                    }
                },
                label = { Text(
                    screen.route.substringAfterLast("/")
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }) }
            )
        }
    }
}