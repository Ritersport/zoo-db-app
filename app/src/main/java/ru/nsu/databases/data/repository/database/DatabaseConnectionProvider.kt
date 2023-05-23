package ru.nsu.databases.data.repository.database

import io.reactivex.Single
import ru.nsu.databases.domain.model.Credentials
import java.sql.Connection

interface DatabaseConnectionProvider {

    fun checkConnection(credentials: Credentials): Single<Boolean>
    fun openConnection(): Connection
}