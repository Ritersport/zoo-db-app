package ru.nsu.databases.data.repository.database.daos.feed_in_stock

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FoodInStock
import ru.nsu.databases.domain.model.zoo.FeedType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodInStockDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : FoodInStockDao {
    override fun getAll(): Single<List<FoodInStock>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FoodInStock> {
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Feed_in_stock\" JOIN (\n" +
                        "      \tSELECT \"Id\" AS feed_type_id, \"Name\" AS feed_type_name\n" +
                        "      \tFROM \"Feed_types\")\n" +
                        "      \tON (\"Type\" = feed_type_id)\n"
            )

            val result: MutableList<FoodInStock> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FoodInStock(
                        feedType = FeedType(
                            rawResult.getInt("feed_type_id"),
                            rawResult.getString("feed_type_name")
                        ),
                        amount = rawResult.getInt("Amount"),
                    )
                )
            }
            return result
        }
    }
}