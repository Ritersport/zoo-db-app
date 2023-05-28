package ru.nsu.databases.data.repository.database.daos.feed_in_stock

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.FoodInStock

interface FoodInStockDao {
    fun getAll(): Single<List<FoodInStock>>
}