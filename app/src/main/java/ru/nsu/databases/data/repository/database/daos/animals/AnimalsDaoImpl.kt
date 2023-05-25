package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.Gender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimalsDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : AnimalsDao {
    override fun getAll(): Single<List<Animal>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<Animal> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Animals\""
            )

            val result: MutableList<Animal> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Animal(
                        id = rawResult.getInt("Id"),
                        kind = rawResult.getInt("Kind"),
                        name = rawResult.getString("Name"),
                        gender = Gender(rawResult.getString("Gender")),
                        birthdate = rawResult.getDate("Birthdate"),
                        father = rawResult.getInt("Father"),
                        mother = rawResult.getInt("Mother"),
                    )
                )
            }
            return result
        }

}