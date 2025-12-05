package com.ubl.todolist.extension

actual object DateFormatHelper {
    actual fun getFormattedDate(
        iso8601Timestamp: Long,
        format: String
    ): String {
        val date = NSDate.dateWithTimeIntervalSince1970(iso8601Timestamp / 1000.0)
        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = format
        dateFormatter.locale = NSLocale(localeIdentifier = "en_US")
        return dateFormatter.stringFromDate(date)
    }
}