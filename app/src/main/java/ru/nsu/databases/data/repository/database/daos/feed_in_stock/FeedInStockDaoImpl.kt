package ru.nsu.databases.data.repository.database.daos.feed_in_stock

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FeedInStock
import ru.nsu.databases.domain.model.zoo.FeedType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedInStockDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : FeedInStockDao {
    override fun getAll(): Single<List<FeedInStock>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FeedInStock> {
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Feed_in_stock\" JOIN (\n" +
                        "      \tSELECT \"Id\" AS feed_type_id, \"Name\" AS feed_type_name\n" +
                        "      \tFROM \"Feed_types\")\n" +
                        "      \tON (\"Type\" = feed_type_id)\n"
            )

            val result: MutableList<FeedInStock> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FeedInStock(
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