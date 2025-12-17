package com.ubl.todolist.extension

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

actual object DateFormatHelper {
    actual fun getFormattedDate(
        iso8601Timestamp: Long,
        format: String
    ): String {
        // 1. Convert Long milliseconds to an Instant
        val instant = Instant.ofEpochMilli(iso8601Timestamp)

        // 2. Create a formatter with the desired pattern and locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
            .withZone(ZoneId.systemDefault()) // Use the system timezone

        // 3. Format the instant
        return formatter.format(instant)
    }
}