package ru.nsu.databases.data.repository.database.daos.diet_type

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.DietType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DietTypeDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : DietTypeDao {
    override fun getAll(): Single<List<DietType>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<DietType> {
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Diet_types\""
            )

            val result : MutableList<DietType> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    DietType(
                        id = rawResult.getInt("Id"),
                        kind = rawResult.getInt("Animal_kind"),
                        minAgeMonth = rawResult.getInt("Min_age_month"),
                        maxAgeMonth = rawResult.getInt("Max_age_month"),
                        seasonStart = rawResult.getInt("Season_start"),
                        seasonEnd = rawResult.getInt("Season_end"),
                    )
                )
            }
            return result
        }
    }
}