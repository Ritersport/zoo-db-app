package ru.nsu.databases.data.repository.database.daos.animal_settling

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.AnimalSettling
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimalSettlingDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : AnimalSettlingDao {

    override fun getAll(): Single<List<AnimalSettling>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<AnimalSettling> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Animal_settling\""
            )

            val result: MutableList<AnimalSettling> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    AnimalSettling(
                        animalId = rawResult.getInt("Animal"),
                        aviaryId = rawResult.getInt("Aviary"),
                        settlingStartDate = rawResult.getDate("Settling_start_date"),
                        settlingEndDate = rawResult.getDate("Settling_end_date")
                    )
                )
            }
            result
        }
}
