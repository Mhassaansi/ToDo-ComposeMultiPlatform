package com.ubl.todolist.extension


import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

expect object DateFormatHelper {
    fun getFormattedDate(
        iso8601Timestamp: Long,
        format: String
    ): String
}


@OptIn(ExperimentalTime::class)
fun getCurrentTimeAsLong(): Long {
    val currentInstant: Instant = Clock.System.now()
    return currentInstant.toEpochMilliseconds()
}