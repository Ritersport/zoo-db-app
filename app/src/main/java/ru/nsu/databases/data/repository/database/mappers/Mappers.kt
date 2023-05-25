package ru.nsu.databases.data.repository.database.mappers

import java.text.SimpleDateFormat
import java.util.*


fun java.sql.Date.toJavaDate(): Date = Date(this.time)
fun Date.toSqlDate(): java.sql.Date = java.sql.Date(this.time)

fun Date.prepareForSqlStatement(): String =
    SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(this).uppercase(Locale.ENGLISH)