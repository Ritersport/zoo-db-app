package ru.nsu.databases.data.repository.database.daos.diet_type

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.DietType

interface DietTypeDao {
    fun getAll(): Single<List<DietType>>
}