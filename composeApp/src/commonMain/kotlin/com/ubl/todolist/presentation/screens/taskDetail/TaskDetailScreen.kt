package com.ubl.todolist.presentation.screens.taskDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.extension.Constant.IS_EDITABLE
import com.ubl.todolist.extension.Constant.TASK_ID
import com.ubl.todolist.presentation.components.TopBarCommon
import com.ubl.todolist.presentation.navigation.NavRoutes
import com.ubl.todolist.presentation.screens.home.TagWidget
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TaskDetailScreen(navController: NavController, taskId: Int? = null) {

    val taskDetailViewModel = koinViewModel<TaskDetailViewModel>()
    val taskUiState: State<TaskUiState> = taskDetailViewModel.uiState.collectAsState()
    LaunchedEffect(true) { taskDetailViewModel.getTaskById(taskId) }
    val task = taskUiState.value


    Scaffold(topBar = {
        TopBarCommon(
            "Task Description",
            showBack = true,
            showEdit = true,
            showDelete = true,
            onBackClick = { navController.popBackStack() },
            onEditClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(IS_EDITABLE, true)
                navController.currentBackStackEntry?.savedStateHandle?.set(TASK_ID, taskId)
                navController.navigate(NavRoutes.AddTaskScreen.route)
            },
            onDeleteClick = {
                taskDetailViewModel.deleteTask(taskId)
                navController.popBackStack()
            })
    }, bottomBar = {}) { paddingValues ->

        remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    task.task?.title?.let {
                        Text(
                            it,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    task.task?.description?.let { Text(it) }
                    Spacer(modifier = Modifier.height(10.dp))
                    TagWidget(task.task?.priority)
                    Spacer(modifier = Modifier.height(10.dp))
                    TaskTimestamps(task.task)
                }
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String?) {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Spacer(modifier = Modifier.width(8.dp))
        value?.let { Text(it) }
    }
}

@Composable
fun TaskTimestamps(task: Task?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoRow(label = "Created At", value = task?.createdAt)
        InfoRow(label = "Updated At", value = task?.updatedAt)
    }
}
