package ru.nsu.databases.data.repository.database.daos.vendor

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FeedType
import ru.nsu.databases.domain.model.zoo.Vendor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VendorsDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : VendorsDao {
    override fun getAll(): Single<List<Vendor>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<Vendor> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult =
                statement.executeQuery("Select * FROM \"Feed_vendors\" JOIN (Select \"Id\" AS \"Feed_type_id\", \"Name\" AS \"Feed_type_name\" FROM \"Feed_types\") ON (\"Feed_type_id\" = \"Feed_type\")\n")

            val result: MutableList<Vendor> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Vendor(
                        id = rawResult.getInt("Id"),
                        organizationName = rawResult.getString("Organisation_name"),
                        feedType = FeedType(
                            id = rawResult.getInt("Feed_type_id"),
                            name = rawResult.getString("Feed_type_name"),
                        )
                    )
                )
            }
            return result
        }
}