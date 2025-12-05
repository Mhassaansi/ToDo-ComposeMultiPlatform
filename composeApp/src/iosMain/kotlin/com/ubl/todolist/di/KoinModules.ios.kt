package com.ubl.todolist.di

import com.ubl.todolist.data.local.AppDatabase
import com.ubl.todolist.db.getDatabaseBuilder
import org.koin.dsl.module


actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
}