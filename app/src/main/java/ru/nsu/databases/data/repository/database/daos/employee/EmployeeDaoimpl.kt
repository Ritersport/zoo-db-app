package ru.nsu.databases.data.repository.database.daos.employee

import ru.nsu.databases.data.repository.database.DatabaseConnectionProvider
import ru.nsu.databases.domain.model.zoo.Employee
import java.sql.PreparedStatement
import javax.inject.Inject


class EmployeeDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider
) : EmployeeDao {

    override fun getAll(): List<Employee> = connectionProvider.openConnection().use { connection ->
        val statement = connection.createStatement()
        val rs = statement.executeQuery("SELECT * FROM EMPLOYEES")

        while (rs.next()) {
            println(
                rs.row.toString() + ". " + rs.getString("FIRST_NAME")
                        + "\t" + rs.getString("LAST_NAME")
            )
        }

        val sql = "select count(*) from EMPLOYEES"
        val preStatement: PreparedStatement = connection.prepareStatement(sql)
        val result = preStatement.executeQuery()
        while (result.next()) {
            val count = result.getInt(1)
            println("Total number of records in EMP table: $count")
        }

        return emptyList()
    }


    private companion object {
        const val EMPLOYEE_QUERY = ""

        const val ID_FIELD = "Id"
        const val GENDER_FIELD = "Gender"
        const val FIRST_NAME_FIELD = "First_name"
        const val LAST_NAME_FIELD = "Last_name"
        const val BIRTHDATE_FIELD = "Id"
        const val PROFESSION_FIELD = "Id"
        const val EMPLOYMENT_DATE_FIELD = "Id"
        const val DISMISSAL_DATE_FIELD = "Id"
    }

}