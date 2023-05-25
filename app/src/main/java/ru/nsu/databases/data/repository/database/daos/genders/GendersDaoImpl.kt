package ru.nsu.databases.data.repository.database.daos.genders

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Gender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GendersDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : GendersDao {

    override fun getAll(): Single<List<Gender>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<Gender> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery("SELECT * FROM \"Genders\"")

            val result: MutableList<Gender> = mutableListOf()
            while (rawResult.next()) {
                result.add(Gender(
                    name = rawResult.getString("Name"),
                ))
            }
            return result
        }
}