package ru.nsu.databases.data.repository.database.daos.species

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Specie
import javax.inject.Inject

class SpeciesDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : SpeciesDao {

    override fun getAll(): Single<List<Specie>> = Single.just(emptyList())

    private fun getAllBlocking(): List<Specie> {

    }
}