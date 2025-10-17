package br.fabiorbap.lotharnews.common.util

import android.util.Log
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.TextStyle
import java.util.Locale

fun String.formatIsoDate(): String {
    try {
        val date = ZonedDateTime.parse(this)
        val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        val day = date.dayOfMonth
        val year = date.year
        val suffix = when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
        return "$month $day$suffix $year"
    } catch (e: DateTimeParseException) {
        Log.e("Error parsing date", this)
        return ""
    }
}