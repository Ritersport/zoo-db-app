package ru.nsu.databases.data.repository.database.daos.animals

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.domain.model.zoo.Animal
import ru.nsu.databases.domain.model.zoo.AnimalParent
import ru.nsu.databases.domain.model.zoo.Gender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimalsDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
    private val speciesDao: SpeciesDao,
) : AnimalsDao {
    override fun getAll(): Single<List<Animal>> = Single.fromCallable(::getAllBlocking)

    override fun getById(id: Int): Single<Animal> = Single.fromCallable { getByIdBlocking(id) }

    override fun getAllAsParent(specieId: Int): Single<List<AnimalParent>> =
        Single.fromCallable { getAllAsParentBlocking(specieId) }

    override fun getWarmCageNeededAnimalIds(): Single<List<Int>> =
        Single.fromCallable(::getWarmCageNeededAnimalIdsBlocking)

    private fun getByIdBlocking(id: Int): Animal =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "select * from (SELECT * FROM \"Animals\" WHERE \"Id\" = $id) LEFT JOIN (SELECT \"Name\" AS \"Father_name\", \"Id\" AS \"Father_id\", \"Kind\" AS \"Father_kind\"  FROM \"Animals\") ON (\"Father_id\" = \"Father\") LEFT JOIN (SELECT \"Name\" AS \"Mother_name\", \"Id\" AS \"Mother_id\", \"Kind\" AS \"Mother_kind\"  FROM \"Animals\") ON (\"Mother_id\" = \"Mother\") "
            )

            rawResult.next()

            Animal(
                id = rawResult.getInt("Id"),
                kind = speciesDao.getById(rawResult.getInt("Kind")).blockingGet(),
                name = rawResult.getString("Name"),
                gender = Gender(rawResult.getString("Gender")),
                birthDate = rawResult.getDate("Birthdate"),
                father = run {
                    val motherId = rawResult.getInt("Mother_id")
                    val motherName = rawResult.getString("Mother_name")
                    val motherKind = rawResult.getInt("Mother_kind")

                    if (motherId != 0 && motherName != null) {
                        AnimalParent(
                            id = motherId,
                            name = motherName,
                            specieId = motherKind,
                        )
                    } else null
                },
                mother = run {
                    val fatherId = rawResult.getInt("Father_id")
                    val fatherName = rawResult.getString("Father_name")
                    val fatherKind = rawResult.getInt("Father_kind")

                    if (fatherId != 0 && fatherName != null) {
                        AnimalParent(
                            id = fatherId,
                            name = fatherName,
                            specieId = fatherKind
                        )
                    } else null
                },
            )
        }


    private fun getAllAsParentBlocking(specieId: Int): List<AnimalParent> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Animals\" WHERE \"Kind\" = $specieId"
            )

            val result: MutableList<AnimalParent> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    AnimalParent(
                        id = rawResult.getInt("Id"),
                        name = rawResult.getString("Name"),
                        specieId = rawResult.getInt("Kind")
                    )
                )
            }
            result
        }

    private fun getAllBlocking(): List<Animal> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Animals\" LEFT JOIN (SELECT \"Name\" AS \"Father_name\", \"Id\" AS \"Father_id\", \"Kind\" AS \"Father_kind\"  FROM \"Animals\") ON (\"Father_id\" = \"Father\") LEFT JOIN (SELECT \"Name\" AS \"Mother_name\", \"Id\" AS \"Mother_id\", \"Kind\" AS \"Mother_kind\"  FROM \"Animals\") ON (\"Mother_id\" = \"Mother\")"
            )
            val species = speciesDao.getAll().blockingGet()

            val result: MutableList<Animal> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Animal(
                        id = rawResult.getInt("Id"),
                        kind = species.find { it.id == rawResult.getInt("Kind") }!!,
                        name = rawResult.getString("Name"),
                        gender = Gender(rawResult.getString("Gender")),
                        birthDate = rawResult.getDate("Birthdate"),
                        mother = run {
                            val motherId = rawResult.getInt("Mother_id")
                            val motherName = rawResult.getString("Mother_name")
                            val motherKind = rawResult.getInt("Mother_kind")

                            if (motherId != 0 && motherName != null) {
                                AnimalParent(
                                    id = motherId,
                                    name = motherName,
                                    specieId = motherKind,
                                )
                            } else null
                        },
                        father = run {
                            val fatherId = rawResult.getInt("Father_id")
                            val fatherName = rawResult.getString("Father_name")
                            val fatherKind = rawResult.getInt("Father_kind")

                            if (fatherId != 0 && fatherName != null) {
                                AnimalParent(
                                    id = fatherId,
                                    name = fatherName,
                                    specieId = fatherKind
                                )
                            } else null
                        },
                    )
                )
            }
            result
        }

    private fun getWarmCageNeededAnimalIdsBlocking(): List<Int> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT *\n" +
                        "FROM \"Animals\" JOIN \"Animal_spicies\" ON (\"Kind\" = \"Animal_spicies\".\"Id\")\n" +
                        "WHERE (\"Warm_aviary\" = 'y') \n"
            )

            val result: MutableList<Int> = mutableListOf()
            while (rawResult.next()) {
                result.add(rawResult.getInt("Id"))
            }
            result
        }
}