package com.ubl.todolist.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource // New Import
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubl.todolist.presentation.components.BottomNavBar
import com.ubl.todolist.presentation.components.TopBarCommon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val name = remember { mutableStateOf("John Doe") }
    val tagline = "Stay productive, stay focused"
    val email = remember { mutableStateOf("johndoe@email.com") }
    val phone = remember { mutableStateOf("+1 234 567 890") }
    val username = remember { mutableStateOf("johndoe123") }

    val defaultPriority = remember { mutableStateOf("Medium") }
    val expanded = remember { mutableStateOf(false) }
    val themeColor = remember { mutableStateOf(Color(0xFF4CAF50)) } // Default green

    Scaffold(
        topBar = {
            TopBarCommon(
                title = "Profile",
                showBack = true,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8E1)) // warm amber background
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section: Profile Picture + Edit
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = name.value.first().toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = { /* Pick from gallery */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = name.value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = tagline,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Personal Info
            InfoCard(title = "Email", value = email.value) { email.value = it }
            InfoCard(title = "Phone", value = phone.value) { phone.value = it }
            InfoCard(title = "Username", value = username.value) { username.value = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Customization Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Customization", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Default Priority dropdown
                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value }
                    ) {
                        OutlinedTextField(
                            value = defaultPriority.value,
                            onValueChange = {},
                            label = { Text("Default Priority") },
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            listOf("Low", "Medium", "High").forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        defaultPriority.value = option
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Theme Accent
                    Text("Profile Theme Accent", fontWeight = FontWeight.Medium)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        listOf(Color(0xFF4CAF50), Color(0xFFFFA000), Color(0xFF1976D2)).forEach { c ->
                            // FIX: Use explicit clickable overload
                            val colorInteractionSource = remember { MutableInteractionSource() }

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(c)
                                    .clickable(
                                        onClick = { themeColor.value = c },
                                        interactionSource = colorInteractionSource,
                                        indication = rememberRipple(bounded = false) // bounded=false for circle ripple
                                    )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Account Management
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Account Management", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    // FIX: Use explicit clickable overload
                    val passwordInteractionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Change Password",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { /* Navigate to change password */ },
                                interactionSource = passwordInteractionSource,
                                indication = rememberRipple()
                            )
                            .padding(12.dp)
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                    // FIX: Use explicit clickable overload
                    val logoutInteractionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Log Out",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { /* Handle logout */ },
                                interactionSource = logoutInteractionSource,
                                indication = rememberRipple()
                            )
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String, onValueChange: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Medium, color = Color.Gray)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}