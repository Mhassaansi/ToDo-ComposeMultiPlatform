package com.ubl.todolist.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ubl.todolist.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


fun getDatabaseBuilder(): AppDatabase {
    val dbFile = "${NSHomeDirectory()}/task.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}