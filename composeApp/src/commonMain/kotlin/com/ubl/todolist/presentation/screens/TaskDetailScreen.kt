package com.ubl.todolist.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.presentation.components.TopBarCommon
import com.ubl.todolist.presentation.navigation.NavRoutes
import com.ubl.todolist.presentation.screens.home.TagWidget

@Composable
fun TaskDetailScreen(navController: NavController,task: Task) {

    Scaffold(
        topBar = {
            TopBarCommon(
                "Task Description",
                showBack = true,
                showEdit = true,
                onBackClick = { navController.popBackStack() },
                onEditClick = {  navController.navigate(NavRoutes.AddTaskScreen.route) }
            )
        },
        bottomBar = {}
    ) { paddingValues ->

        val checked = remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // reasonable padding inside the card
                ) {

                    Text("Task 1", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                                "sed do eiusmod tempor incididunt ut labore et dolore magna " +
                                "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                                "ullamco laboris nisi ut aliquip ex ea commodo consequat."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Due : 2025-10-01", fontSize = 20.sp,
                            fontStyle = FontStyle.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TagWidget("High")
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Blue.copy(alpha = 0.05f))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Status", fontSize = 20.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            "Pending", fontSize = 20.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Switch(
                            checked = checked.value,
                            onCheckedChange = { checked.value = it }
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Completed", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Created On: 2025-10-01")
                        Spacer(modifier = Modifier.height(4.dp))  // Small space between texts
                        Text("Update On: 2025-10-01")
                    }
                }
            }
        }
    }
}


