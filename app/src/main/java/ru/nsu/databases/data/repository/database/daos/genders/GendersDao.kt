package ru.nsu.databases.data.repository.database.daos.genders

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Gender

interface GendersDao {

    fun getAll(): Single<List<Gender>>
}