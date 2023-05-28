package ru.nsu.databases.data.repository.database.daos.feed_rations

import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.DietType
import ru.nsu.databases.domain.model.zoo.FeedRation
import ru.nsu.databases.domain.model.zoo.FeedType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRationDaoImpl @Inject constructor(
    val connectionProvider: DatabaseConnectionProvider,
) : FeedRationDao {
    override fun getAll(): Single<List<FeedRation>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<FeedRation> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM (\"Feed_rations\" JOIN (\n" +
                        "SELECT  \"Id\" AS feed_type_id, \"Name\" AS feed_type_name \n" +
                        "FROM \"Feed_types\") ON (\"Feed_type\" = feed_type_id)) JOIN (\n" +
                        "\n" +
                        "SELECT  \"Id\" AS diet_type_id, \"Animal_kind\" AS kind, \"Min_age_month\" as min_age, \"Max_age_month\" as max_age, \"Season_start\" as season_start, \"Season_end\" as season_end \n" +
                        "FROM \"Diet_types\") ON (\"Diet_type\" = diet_type_id)"
            )
            val result: MutableList<FeedRation> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    FeedRation(
                        dietType = DietType(
                            rawResult.getInt("diet_type_id"),
                            rawResult.getInt("kind"),
                            rawResult.getInt("min_age"),
                            rawResult.getInt("max_age"),
                            rawResult.getInt("season_start"),
                            rawResult.getInt("season_end")
                        ),
                        feedType = FeedType(
                            rawResult.getInt("feed_type_id"),
                            rawResult.getString("feed_type_name")
                        ),
                        amount = rawResult.getInt("\"Amount_kg\"")
                    )
                )
            }
            result
        }

}
