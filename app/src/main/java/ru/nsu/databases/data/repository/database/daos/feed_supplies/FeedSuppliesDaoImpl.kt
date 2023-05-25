package ru.nsu.databases.data.repository.database.daos.feed_supplies

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FeedSupply
import ru.nsu.databases.domain.model.zoo.Vendor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedSuppliesDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : FeedSuppliesDao {
    override fun getAll(): Single<List<FeedSupply>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FeedSupply> {
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Feed_supplies\" JOIN (Select \"Id\" AS \"Vendor_id\", \"Organisation_name\", \"Feed_type\" FROM \"Feed_vendors\") ON (\"Vendor\" = \"Vendor_id\")\n"
            )

            val result: MutableList<FeedSupply> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FeedSupply(
                        vendor = Vendor(
                            id = rawResult.getInt("Vendor_id"),
                            organizationName = rawResult.getString("Organisation_name"),
                            feedType = rawResult.getInt("Feed_type"),
                        ),
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