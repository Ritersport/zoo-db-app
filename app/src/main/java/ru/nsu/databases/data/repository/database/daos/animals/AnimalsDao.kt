package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.domain.model.zoo.Employee

interface AnimalsDao {
    fun getAll(): Single<List<Animal>>

    fun add(animal: Animal): Completable

    fun getById(id: Int): Single<Animal>

    fun update(animal: Animal): Completable

    fun getAllAsParent(specieId: Int): Single<List<AnimalParent>>

    fun getWarmCageNeededAnimalIds(): Single<List<Int>>

    fun getMoveNeeded(): Single<List<Pair<Animal, Animal>>>
}