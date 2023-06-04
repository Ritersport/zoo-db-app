package ru.nsu.databases.data.repository.database.daos.species

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.IncompatibleSpecies
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie
import ru.nsu.databases.domain.model.zoo.SpecieShort
import javax.inject.Inject

class SpeciesDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : SpeciesDao {

    override fun getAll(): Single<List<Specie>> = Single.fromCallable(::getAllBlocking)
    override fun getById(id: Int): Single<Specie> = Single.fromCallable { getByIdBlocking(id) }
    override fun getIncompatibleSpecies(): Single<List<IncompatibleSpecies>> =
        Single.fromCallable(::getIncompatibleSpeciesBlocking)

    private fun getIncompatibleSpeciesBlocking(): List<IncompatibleSpecies> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult =
                statement.executeQuery("SELECT * FROM \"Incompatible_species\" JOIN (SELECT \"Id\" AS \"Id_1\", \"Name\" AS \"Name_1\" FROM \"Animal_spicies\") ON (\"Kind_1\" = \"Id_1\") JOIN (SELECT \"Id\" AS \"Id_2\", \"Name\" AS \"Name_2\" FROM \"Animal_spicies\") ON (\"Kind_2\" = \"Id_2\") ")

            val result: MutableList<IncompatibleSpecies> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    IncompatibleSpecies(
                        specie1 = SpecieShort(rawResult.getString("Name_1")),
                        specie2 = SpecieShort(rawResult.getString("Name_2")),
                    )
                )
            }
            result
        }

    private fun getByIdBlocking(id: Int): Specie =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult =
                statement.executeQuery("SELECT * FROM \"Animal_spicies\" JOIN (Select \"Id\" AS \"Nutrition_id\", \"Name\" AS \"Nutrition_Name\" FROM \"Nutrition_types\") ON (\"Nutrition_type\" = \"Nutrition_id\") WHERE (\"Id\" = $id)")

            rawResult.next()
            Specie(
                id = rawResult.getInt("Id"),
                name = rawResult.getString("Name"),
                nutritionType = NutritionType(
                    id = rawResult.getInt("Nutrition_id"),
                    name = rawResult.getString("Nutrition_Name"),
                ),
                warmAviary = rawResult.getString("Warm_aviary"),
                reproductiveAgeMinMonth = rawResult.getInt("Reproductive_age_min_month"),
                reproductiveAgeMaxMonth = rawResult.getInt("Reproductive_age_max_month"),
            )
        }

    private fun getAllBlocking(): List<Specie> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult =
                statement.executeQuery("SELECT * FROM \"Animal_spicies\" JOIN (Select \"Id\" AS \"Nutrition_id\", \"Name\" AS \"Nutrition_Name\" FROM \"Nutrition_types\") ON (\"Nutrition_type\" = \"Nutrition_id\")")

            val result: MutableList<Specie> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Specie(
                        id = rawResult.getInt("Id"),
                        name = rawResult.getString("Name"),
                        nutritionType = NutritionType(
                            id = rawResult.getInt("Nutrition_id"),
                            name = rawResult.getString("Nutrition_Name"),
                        ),
                        warmAviary = rawResult.getString("Warm_aviary"),
                        reproductiveAgeMinMonth = rawResult.getInt("Reproductive_age_min_month"),
                        reproductiveAgeMaxMonth = rawResult.getInt("Reproductive_age_max_month"),
                    )
                )
            }
            result
        }
}