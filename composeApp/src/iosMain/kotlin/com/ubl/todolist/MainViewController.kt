package com.ubl.todolist

import androidx.compose.ui.window.ComposeUIViewController
import com.ubl.todolist.di.commonModules
import com.ubl.todolist.di.initKoin
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}