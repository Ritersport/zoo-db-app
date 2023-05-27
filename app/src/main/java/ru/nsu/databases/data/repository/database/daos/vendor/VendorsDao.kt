package ru.nsu.databases.data.repository.database.daos.vendor

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Vendor

interface VendorsDao {

    fun getAll(): Single<List<Vendor>>
}