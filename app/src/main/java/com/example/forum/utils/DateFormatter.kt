package com.example.forum.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
object DateFormatter {
    fun formatDateShort(timestamp: Long): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(Date(timestamp))
    }
}