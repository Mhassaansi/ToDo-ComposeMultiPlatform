package com.ubl.todolist.presentation.screens.home


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubl.todolist.presentation.components.BottomNavBar
import com.ubl.todolist.presentation.components.TopBarTasks
import com.ubl.todolist.presentation.navigation.NavRoutes
import com.ubl.todolist.presentation.theme.LightHeaderGradient
import org.koin.compose.viewmodel.koinViewModel


data class TaskData(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: String = "",
    val createDate: String = ""
)

@Composable
fun HomeScreen(navController: NavController) {

    val menuExpanded = remember { mutableStateOf(false) }

    val viewModel = koinViewModel<HomeScreenViewModel>()

    Scaffold(
        topBar = {
            TopBarTasks(
                title = "My Tasks",
                onSearchClick = {},
                onMenuClick = { menuExpanded.value = true }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
           LazyColumn {
                items(items = viewModel.uiState.value.tasks, key = { it.id }) { task ->
                    TaskCard(
                        taskData = TaskData(
                            id = task.id,
                            title = task.title,
                            priority = task.priority,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            createDate = task.createdAt
                        ),
                        onCheckedChange = {

                        },
                        navController = navController
                    )
                }
            }

            // FAB
            AddTaskButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                navController.navigate(NavRoutes.AddTaskScreen.route)
            }

            // Dropdown menu (top-end)
            Box(modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopEnd)
                .padding(top = 56.dp, end = 8.dp) // align below TopBar menu icon
            ) {
              /*  TaskMenuDropdown(
                    expanded = menuExpanded.value,
                    onDismissRequest = { menuExpanded.value = false },


                    onPrioritySelected = { selectedPriority ->
                        menuExpanded.value = false
                        visibleTasks.clear()
                        visibleTasks.addAll(
                            tasks.filter {
                                it.priority.equals(selectedPriority, ignoreCase = true)
                            }
                        )
                    }
                    ,


                    onSortSelected = { isAscending ->
                        menuExpanded.value = false
                    },
                    onClearFilter = {
                        menuExpanded.value = false
                        visibleTasks.clear()
                        visibleTasks.addAll(tasks)
                    }
                )*/
            }
        }
    }
}

@Composable
fun TaskCard(
    taskData: TaskData,
    onCheckedChange: (Boolean) -> Unit = {},
    navController: NavController
) {
    val textDecoration = if (taskData.isCompleted)
        TextDecoration.LineThrough else TextDecoration.None
    val cardAlpha = if (taskData.isCompleted) 0.5f else 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .alpha(cardAlpha),
        shape = CutCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = {navController.navigate(NavRoutes.TaskDetailScreen.route)}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Checkbox(
                checked = taskData.isCompleted,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = taskData.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = textDecoration
                    )
                )
                Text(
                    text = taskData.createDate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                        textDecoration = textDecoration
                    )
                )
            }
            TagWidget(priority = taskData.priority)
        }
    }
}


@Composable
fun TagWidget(priority: String) {
    val (text, color) = when (priority.lowercase()) {
        "high" -> "High" to Color.Red
        "medium" -> "Medium" to Color(0xFFFFA500) // Orange-ish
        "low" -> "Low" to Color.Green
        else -> "None" to Color.Gray
    }

    Box(
        modifier = Modifier
            .background(color = color.copy(alpha = 0.2f), shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AddTaskButton(modifier: Modifier,onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current
    Box(
        modifier = modifier
            .size(56.dp)
            .background(brush = LightHeaderGradient, shape = CircleShape)
            .clickable(onClick = onClick,
                indication = indication,
                interactionSource = interactionSource
                ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Task",
            tint = Color.White
        )
    }
}

@Composable
fun TaskMenuDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onPrioritySelected: (String) -> Unit,
    onSortSelected: (Boolean) -> Unit,
    onClearFilter: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        // Priority Section
        DropdownMenuItem(
            text = { Text("High Priority") },
            onClick = { onPrioritySelected("High") },
            leadingIcon = {
                Icon(Icons.Default.PriorityHigh, contentDescription = "High Priority",
                    tint = Color.Red)
            }
        )
        DropdownMenuItem(
            text = { Text("Medium Priority") },
            onClick = { onPrioritySelected("Medium") },
            leadingIcon = {
                Icon(Icons.Default.Report, contentDescription = "Medium Priority",
                    tint = Color(0xFFFFA500))
            }
        )
        DropdownMenuItem(
            text = { Text("Low Priority") },
            onClick = { onPrioritySelected("Low") },
            leadingIcon = {
                Icon(Icons.Default.KeyboardArrowDown,
                    contentDescription = "Low Priority", tint = Color.Green)
            }
        )

        Divider()


        DropdownMenuItem(
            text = { Text("Sort Ascending") },
            onClick = { onSortSelected(true) },
            leadingIcon = {
                Icon(Icons.Default.ArrowUpward, contentDescription = "Ascending")
            }
        )
        DropdownMenuItem(
            text = { Text("Sort Descending") },
            onClick = { onSortSelected(false) },
            leadingIcon = {
                Icon(Icons.Default.ArrowDownward, contentDescription = "Descending")
            }
        )

        DropdownMenuItem(
            text = { Text("Clear Filters") },
            onClick = onClearFilter,
            leadingIcon = {
                Icon(Icons.Default.Close, contentDescription = "Clear Filters",
                    tint = Color.Gray)
            }
        )
    }
}


