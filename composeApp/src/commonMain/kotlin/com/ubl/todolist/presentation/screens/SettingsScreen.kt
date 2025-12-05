package com.ubl.todolist.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple // Needed for the ripple effect
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubl.todolist.presentation.components.BottomNavBar
import com.ubl.todolist.presentation.components.TopBarCommon

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopBarCommon(title = "Settings", showBack = true,
                onBackClick = { navController.popBackStack() })
        },
        bottomBar = {BottomNavBar(navController)}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)) // light background
                .verticalScroll(rememberScrollState())
        ) {

            SettingsSection("General") {
                SettingSwitchItem("Notifications", true) {}
                SettingDropdownItem("Default Due Time", "5 PM", listOf("9 AM", "12 PM", "5 PM"))
                SettingSwitchItem("Dark Mode", false) {}
            }

            SettingsSection("Task Management") {
                SettingDropdownItem("Sort Tasks By", "Priority", listOf("Newest", "Oldest", "Priority", "Due Date"))
                SettingSwitchItem("Show Completed Tasks", true) {}
            }

            SettingsSection("Backup & Sync") {
                SettingClickableItem("Backup Data") {}
                SettingClickableItem("Restore Data") {}
            }

            SettingsSection("Privacy & Security") {
                SettingSwitchItem("Enable App Lock", false) {}
                SettingClickableItem("Privacy Policy") {}
            }

            SettingsSection("About") {
                SettingClickableItem("About the App") {}
                SettingClickableItem("Feedback / Contact Us") {}
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun SettingSwitchItem(title: String, initialChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    var checked = remember { mutableStateOf(initialChecked) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    checked.value = !checked.value
                    onCheckedChange(checked.value)
                },
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                onCheckedChange(it)
            }
        )
    }
    Divider()
}

@Composable
fun SettingDropdownItem(title: String, selected: String, options: List<String>) {
    var expanded = remember { mutableStateOf(false) }
    var current = remember { mutableStateOf(selected) }
    // Define the required interaction source
    val interactionSource = remember { MutableInteractionSource() }


    Column(
        Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { expanded.value = true },
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(6.dp))
        Text(current.value, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }

    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    current.value = option
                    expanded.value = false
                }
            )
        }
    }

    Divider()
}

@Composable
fun SettingClickableItem(title: String, onClick: () -> Unit) {
    // Define the required interaction source
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            // FIX: Use the explicit clickable overload
            .clickable(
                onClick = { onClick() },
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    )
    Divider()
}