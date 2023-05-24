package ru.nsu.databases.data.repository.database.mappers


fun java.sql.Date.toJavaDate(): java.util.Date = java.util.Date(this.time)
fun java.util.Date.toSqlDate(): java.sql.Date = java.sql.Date(this.time)