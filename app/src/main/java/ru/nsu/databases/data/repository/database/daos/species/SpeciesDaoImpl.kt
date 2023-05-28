package ru.nsu.databases.data.repository.database.daos.species

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.NutritionType
import ru.nsu.databases.domain.model.zoo.Specie
import javax.inject.Inject

class SpeciesDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : SpeciesDao {

    override fun getAll(): Single<List<Specie>> = Single.fromCallable(::getAllBlocking)
    override fun getById(id: Int): Single<Specie> = Single.fromCallable { getByIdBlocking(id) }

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