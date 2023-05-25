package ru.nsu.databases.data.repository.database.daos.nutrition_type

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.NutritionType

interface NutritionTypeDao {

    fun getAll(): Single<List<NutritionType>>
}