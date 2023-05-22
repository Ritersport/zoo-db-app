package ru.nsu.databases.data.repository.database

import java.sql.Connection

interface DatabaseConnectionProvider {

    fun openConnection(): Connection
}