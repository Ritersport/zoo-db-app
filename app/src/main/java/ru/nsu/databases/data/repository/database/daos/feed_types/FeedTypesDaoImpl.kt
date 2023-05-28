package ru.nsu.databases.data.repository.database.daos.feed_types

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.FeedType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedTypesDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : FeedTypesDao {
    override fun getAll(): Single<List<FeedType>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FeedType> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Feed_types\"\n"
            )

            val result: MutableList<FeedType> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FeedType(
                        id = rawResult.getInt("Id"),
                        name = rawResult.getString("Name")
                    )
                )
            }
            result
        }

}