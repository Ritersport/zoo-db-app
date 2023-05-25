package ru.nsu.databases.data.repository.database.daos.feed_supplies

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.FeedSupply

interface FeedSuppliesDao {
    fun getAll(): Single<List<FeedSupply>>
}