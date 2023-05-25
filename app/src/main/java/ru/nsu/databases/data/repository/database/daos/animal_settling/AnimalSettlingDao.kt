package ru.nsu.databases.data.repository.database.daos.animal_settling

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.AnimalSettling

interface AnimalSettlingDao {
    fun getAll() : Single<List<AnimalSettling>>
}