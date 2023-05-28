package ru.nsu.databases.data.repository.database.daos.nutrition_type

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.NutritionType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NutritionTypeDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : NutritionTypeDao {

    override fun getAll(): Single<List<NutritionType>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<NutritionType> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery("SELECT * FROM \"Nutrition_types\"")

            val result: MutableList<NutritionType> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    NutritionType(
                        id = rawResult.getInt("Id"),
                        name = rawResult.getString("Name"),
                    )
                )
            }
            result
        }
}