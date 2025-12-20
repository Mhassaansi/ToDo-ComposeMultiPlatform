package com.ubl.todolist

import androidx.compose.ui.window.ComposeUIViewController
import com.ubl.todolist.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}