package com.ns.fakex.utils

import android.util.Base64
import java.security.MessageDigest
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun generateCodeVerifier(): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..64)
        .map { charset.random() }
        .joinToString("")
}

fun generateCodeChallenge(codeVerifier: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(codeVerifier.toByteArray())
    return Base64.encodeToString(hash, Base64.URL_SAFE or Base64.NO_WRAP)
        .replace("=", "")
        .replace("+", "-")
        .replace("/", "_")
}

fun String.formatTweetDate(): String {
        val parsedDate = ZonedDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        val time = parsedDate.format(timeFormatter)
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy", Locale("tr"))
        val date = parsedDate.format(dateFormatter)
        return "$time \u2022 $date"
}
