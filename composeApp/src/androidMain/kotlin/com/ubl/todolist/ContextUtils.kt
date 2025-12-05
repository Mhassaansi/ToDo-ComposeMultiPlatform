package com.ubl.todolist

import android.content.Context

object ContextUtils {

    private var todolistAppContext: Context? = null

    val context
        get() = todolistAppContext
            ?: error("Android context has not been set. Please call setContext in your Application's onCreate.")

    fun setContext(context: Context) {
        todolistAppContext = context.applicationContext
    }
}