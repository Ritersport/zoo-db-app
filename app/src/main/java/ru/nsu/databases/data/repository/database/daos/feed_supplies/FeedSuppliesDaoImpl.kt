package ru.nsu.databases.data.repository.database.daos.feed_supplies

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FeedSupply
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedSuppliesDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
): FeedSuppliesDao{
    override fun getAll(): Single<List<FeedSupply>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FeedSupply> {
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Feed_supplies\"\n"
            )

            val result: MutableList<FeedSupply> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FeedSupply(
                        vendor = rawResult.getInt("Vendor"),
                        supplyDate = rawResult.getDate("Supply_date"),
                        amount = rawResult.getInt("Amount_kg"),
                        price = rawResult.getInt("Price_for_kg")
                    )
                )
            }
            return result
        }
    }
}