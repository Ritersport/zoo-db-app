package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Animal
import javax.inject.Inject

class AnimalsDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : AnimalsDao {

    override fun getAll(): Single<List<Animal>> = Single.just(emptyList())
}