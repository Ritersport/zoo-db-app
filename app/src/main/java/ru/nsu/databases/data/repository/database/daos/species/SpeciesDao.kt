package ru.nsu.databases.data.repository.database.daos.species

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Specie

interface SpeciesDao {

    fun getAll(): Single<List<Specie>>
}