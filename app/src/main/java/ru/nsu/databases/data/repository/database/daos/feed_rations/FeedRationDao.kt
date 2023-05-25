package ru.nsu.databases.data.repository.database.daos.feed_rations

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.FeedRation

interface FeedRationDao {
    fun getAll(): Single<List<FeedRation>>
}