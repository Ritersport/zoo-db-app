package ru.nsu.databases.data.repository.database.connection_provider

import io.reactivex.Single
import ru.nsu.databases.domain.model.security.Credentials
import java.sql.Connection

interface DatabaseConnectionProvider {

    fun checkConnection(credentials: Credentials): Single<Credentials>
    fun openConnection(): Connection
}