package com.ubl.todolist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ubl.todolist.di.commonModules
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDo List Cmp App",
    ) {

        startKoin {
            modules(commonModules)
        }
        App()
    }
}