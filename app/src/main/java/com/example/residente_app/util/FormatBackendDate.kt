package com.example.residente_app.util
import java.util.Locale

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

fun formatFecha(fechaISO: String): String {
    val parsed = ZonedDateTime.parse(fechaISO)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return parsed.format(formatter)
}

fun generateValidUntilISO(minutesToAdd: Int): String {
    return OffsetDateTime
        .now(ZoneOffset.UTC)
        .plusMinutes(minutesToAdd.toLong())
        .toString()
}