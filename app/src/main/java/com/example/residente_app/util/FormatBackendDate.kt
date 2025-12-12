package com.example.residente_app.util
import java.util.Locale

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

fun formatFecha(fechaISO: String): String {
    val parsed = ZonedDateTime.parse(fechaISO)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return parsed.format(formatter)
}