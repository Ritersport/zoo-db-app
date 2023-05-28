package ru.nsu.databases.data.repository.database.daos.professions

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Profession
import javax.inject.Inject

class ProfessionsDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : ProfessionsDao {

    override fun getAll(): Single<List<Profession>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<Profession> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery("SELECT * FROM \"Professions\" ")
            val result: MutableList<Profession> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Profession(
                        id = rawResult.getInt("Id"),
                        name = rawResult.getString("Name"),
                    )
                )
            }
            result
        }
}