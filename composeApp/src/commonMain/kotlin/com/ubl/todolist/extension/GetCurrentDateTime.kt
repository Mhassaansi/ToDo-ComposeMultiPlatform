package com.ubl.todolist.extension

import com.ubl.todolist.extension.DateFormatHelper.getFormattedDate


fun Long?.toStringFormat(): String {
    if (this == null) return ""
    return getFormattedDate(this, "dd MMM yyyy, HH:mm")
}