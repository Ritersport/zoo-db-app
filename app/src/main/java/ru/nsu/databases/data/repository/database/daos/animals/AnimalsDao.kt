package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent

interface AnimalsDao {
    fun getAll(): Single<List<Animal>>

    fun getAllAsParent(specieId: Int): Single<List<AnimalParent>>

    fun getWarmCageNeededAnimalIds(): Single<List<Int>>
}