package com.ubl.todolist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ubl.todolist.di.initKoin


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDo List Cmp App",
    ) {
        initKoin()

        App()
    }
}