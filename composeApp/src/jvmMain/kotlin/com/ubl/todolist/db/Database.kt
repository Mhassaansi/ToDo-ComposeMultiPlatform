package com.ubl.todolist.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ubl.todolist.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import java.io.File



fun getDatabaseBuilder(): AppDatabase {
    val dbPath: File =  File(System.getProperty("java.io.tmpdir"), "task.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbPath.absolutePath,
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()}


