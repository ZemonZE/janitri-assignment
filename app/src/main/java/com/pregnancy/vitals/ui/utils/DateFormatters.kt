package com.pregnancy.vitals.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val vitalsDateFormat : DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE, dd MMM yyyy hh:mm a")

fun formatVitalsDate(timestamp: Long):String{
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(vitalsDateFormat)
}