package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Animal

interface AnimalsDao {
    fun getAll(): Single<List<Animal>>
}