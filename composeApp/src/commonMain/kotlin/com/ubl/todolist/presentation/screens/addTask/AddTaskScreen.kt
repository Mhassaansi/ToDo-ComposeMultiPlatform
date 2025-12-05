package com.ubl.todolist.presentation.screens.addTask

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ubl.todolist.data.local.Task
import com.ubl.todolist.extension.getCurrentTimeAsLong
import com.ubl.todolist.extension.toStringFormat
import com.ubl.todolist.presentation.components.TopBarCommon
import com.ubl.todolist.presentation.theme.LightPrimary
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AddTaskScreen(navController: NavController) {


    val viewModel = koinViewModel<AddTaskViewModel>()
    val scope = rememberCoroutineScope()

    val currentTime = getCurrentTimeAsLong()
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val selectedPriority = remember { mutableStateOf("Medium") }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    val newTask = Task(
        title = title.value,
        description = description.value,
        priority = selectedPriority.value,
        isCompleted = false,
        createdAt = currentTime.toStringFormat(),
        updatedAt = currentTime.toStringFormat()
    )


    LaunchedEffect(Unit) {
            viewModel.saveSuccessEvent.collect {
                snackBarHostState.showSnackbar(
                    message = "Task saved successfully!",
                    actionLabel = "View",
                    duration = SnackbarDuration.Short
                )
                navController.popBackStack()
            }
            }


    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarCommon(
                title = "Add Task", showBack = true, onBackClick = { navController.popBackStack()})},
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    buildAnnotatedString {
                        append("Title ")
                        withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
                    }, fontSize = 15.sp, modifier = Modifier.padding(top = 12.dp)
                )
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Enter title") })

                Text(
                    buildAnnotatedString {
                        append("Description ")
                        withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
                    }, fontSize = 15.sp, modifier = Modifier.padding(top = 12.dp)
                )

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Enter description") })

                Text("Priority", fontSize = 15.sp, modifier = Modifier.padding(top = 12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    PriorityChip("Low", Color.Green,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedPriority.value = "low"})
                    PriorityChip("Medium", Color(0xFFFFA500),
                        modifier = Modifier.weight(1f),
                        onClick = { selectedPriority.value = "medium"})
                    PriorityChip("High", Color.Red,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedPriority.value = "high"})
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 8.dp)
                ) {

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White, contentColor = Color.Gray
                        )
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (title.value.isBlank() || description.value.isBlank()) {
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Title and Description are required!",
                                        actionLabel = "OK",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                return@Button
                            }
                            viewModel.insertTask(newTask)
                        },
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightPrimary, contentColor = Color.White
                        )
                    ) {
                        Text("Save")
                    }
                }

            }
        }
    }
}


@Composable
fun PriorityChip(
    text: String, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Surface(
        shape = RoundedCornerShape(50),
        color = color.copy(alpha = 0.3f),
        modifier = modifier.height(40.dp).clickable(
                interactionSource = interactionSource, onClick = onClick, indication = indication
            )
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text, color = color, style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
