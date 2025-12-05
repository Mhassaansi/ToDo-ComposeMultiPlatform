package com.ubl.todolist.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.ubl.todolist.data.local.AppDatabase
import com.ubl.todolist.data.local.TaskDao
import com.ubl.todolist.data.local.TaskRepositoryImpl
import com.ubl.todolist.domain.repository.TaskRepository
import com.ubl.todolist.domain.usecase.CreateTaskUseCase
import com.ubl.todolist.domain.usecase.DeleteTaskUseCase
import com.ubl.todolist.domain.usecase.GetAllTaskUseCase
import com.ubl.todolist.domain.usecase.GetTaskByIdUseCase
import com.ubl.todolist.domain.usecase.UpdateTaskUseCase
import com.ubl.todolist.presentation.screens.addTask.AddTaskViewModel
import com.ubl.todolist.presentation.screens.home.HomeScreenViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind


import org.koin.dsl.module
import kotlin.coroutines.EmptyCoroutineContext.get


expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
        config?.invoke(this)
        modules(repositoryModule,useCaseModule,viewModelModule,platformModule())
    }


val repositoryModule = module {
    singleOf(::TaskRepositoryImpl).bind(TaskRepository::class)
}

val useCaseModule = module {
    singleOf(::CreateTaskUseCase)
    singleOf(::UpdateTaskUseCase)
    singleOf(::DeleteTaskUseCase)
    singleOf(::GetAllTaskUseCase)
    singleOf(::GetTaskByIdUseCase)

}
val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::AddTaskViewModel)
}






