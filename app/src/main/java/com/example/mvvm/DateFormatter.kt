package com.example.mvvm

import android.icu.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val format = SimpleDateFormat("dd MMM yy", Locale.getDefault())

    fun format(date: Date): String =
        format.format(date)
}