package com.ubl.todolist.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ubl.todolist.presentation.theme.AppTypography
import com.ubl.todolist.presentation.theme.LightHeaderGradient
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onTimeOut: ()  -> Unit) {

    LaunchedEffect(Unit) {
        delay(3000)
        onTimeOut()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = LightHeaderGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Todo Master",
                style = AppTypography.labelLarge.copy(
                    fontSize = 24.sp // Adjust text size
                ),
                color = Color.White
            )
        }
    }
}



