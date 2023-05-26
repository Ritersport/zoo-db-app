package ru.nsu.databases.data.repository.database.connection_provider

import io.reactivex.Single
import ru.nsu.databases.domain.model.security.Credentials
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import java.lang.IllegalStateException
import java.sql.Connection
import java.sql.DriverManager
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OracleDatabaseConnectionProviderImpl @Inject constructor(
    private val credentialsStorage: CredentialsStorage,
) : DatabaseConnectionProvider {

    override fun checkConnection(credentials: Credentials): Single<Credentials> {
        Class.forName(ORACLE_JDBC_DRIVER)

        return Single.fromCallable {
            val timeZone: TimeZone = TimeZone.getTimeZone("GMT+7")
            TimeZone.setDefault(timeZone)
            Locale.setDefault(Locale.ENGLISH)

            DriverManager.getConnection(
                URL.format(IP),
                credentials.login,
                credentials.password
            )

            credentials
        }
    }

    override fun openConnection(): Connection {
        Class.forName(ORACLE_JDBC_DRIVER)

        val timeZone: TimeZone = TimeZone.getTimeZone("GMT+7")
        TimeZone.setDefault(timeZone)
        Locale.setDefault(Locale.ENGLISH)

        val creds = credentialsStorage.getCredentialsBlocking()
        return DriverManager.getConnection(
            URL.format(IP),
            creds.login,
            creds.password
        )
    }

    companion object {

        private const val ORACLE_JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"
        private const val IP = "84.237.50.81:1521"
        private const val URL = "jdbc:oracle:thin:@%s:"
    }
}