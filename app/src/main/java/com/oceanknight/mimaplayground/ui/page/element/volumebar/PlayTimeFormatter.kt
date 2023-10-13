package com.oceanknight.mimaplayground.ui.page.element.volumebar

import java.util.concurrent.TimeUnit

/**
 *
 * @author: Oceanknight
 * @date: 2023/10/12
 * @describe:
 */
fun Long.convertSecondToMinute(): String {
    val minutes = TimeUnit.SECONDS.toMinutes(this)
    val seconds = this - 60 * minutes
    return buildString {
        if (minutes < 10) append(0)
        append(minutes)
        append(":")
        if (seconds < 10) append(0)
        append(seconds)
    }
}