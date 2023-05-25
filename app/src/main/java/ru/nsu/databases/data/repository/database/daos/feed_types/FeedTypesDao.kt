package ru.nsu.databases.data.repository.database.daos.feed_types

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.FeedType

interface FeedTypesDao {
    fun getAll(): Single<List<FeedType>>
}