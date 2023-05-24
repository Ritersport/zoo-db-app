package ru.nsu.databases.data.repository.database.daos.professions

import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Profession

interface ProfessionsDao {

    fun getAll(): Single<List<Profession>>
}