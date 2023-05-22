package ru.nsu.databases.data.repository.database

import ru.nsu.databases.domain.reposiroty.CredentialsRepository
import java.sql.Connection
import java.sql.DriverManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OracleDatabaseConnectionProviderImpl @Inject constructor(
    private val credentialsRepository: CredentialsRepository,
) : DatabaseConnectionProvider {

    override fun openConnection(): Connection {
        Class.forName(ORACLE_JDBC_DRIVER)

        val creds = credentialsRepository.getCredentialsBlocking()
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