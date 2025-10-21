package br.fabiorbap.lotharnews.common.util

import android.util.Log
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.TextStyle
import java.util.Locale

private const val TEEN_START = 11
private const val TEEN_END = 13
private const val DAY_DIVIDER = 10
private const val FIRST = 1
private const val SECOND = 2
private const val THIRD = 3

fun String.formatDate(): String {
    try {
        val date = ZonedDateTime.parse(this)
        val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        val day = date.dayOfMonth
        val year = date.year
        val suffix =
            when {
                day in TEEN_START..TEEN_END -> "th"
                day % DAY_DIVIDER == FIRST -> "st"
                day % DAY_DIVIDER == SECOND -> "nd"
                day % DAY_DIVIDER == THIRD -> "rd"
                else -> "th"
            }
        return "$month $day$suffix $year"
    } catch (e: DateTimeParseException) {
        Log.e("Error parsing date", this)
        return ""
    }
}
