package ru.nsu.databases.data.repository.database.daos.employee

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.mappers.prepareForSqlStatement
import ru.nsu.databases.domain.model.zoo.Employee
import ru.nsu.databases.domain.model.zoo.Gender
import ru.nsu.databases.domain.model.zoo.Profession
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EmployeeDaoImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : EmployeeDao {

    override fun getAll(): Single<List<Employee>> = Single.fromCallable(::getAllBlocking)

    private fun getAllBlocking(): List<Employee> =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            val rawResult = statement.executeQuery(
                "SELECT * FROM \"Employees\" JOIN (\n" +
                        "      \tSELECT \"Id\" AS profession_id, \"Name\" AS profession_name\n" +
                        "      \tFROM \"Professions\")\n" +
                        "      \tON (\"Profession\" = profession_id)\n"
            )

            val result: MutableList<Employee> = mutableListOf()
            while (rawResult.next()) {
                result.add(
                    Employee(
                        id = rawResult.getInt("Id"),
                        gender = Gender(rawResult.getString("Gender")),
                        name = rawResult.getString("First_name"),
                        surname = rawResult.getString("Last_name"),
                        patronymic = rawResult.getString("Patronymic"),
                        birthDate = rawResult.getDate("Birthdate"),
                        profession = Profession(
                            id = rawResult.getInt("PROFESSION_ID"),
                            name = rawResult.getString("PROFESSION_NAME")
                        ),
                        salary = rawResult.getInt("Salary"),
                        dismissalDate = rawResult.getDate("Dismissal_date"),
                        employmentDate = rawResult.getDate("Employment_date"),
                    )
                )
            }
            result
        }

    override fun update(employee: Employee): Completable = Completable.fromAction {
        updateBlocking(employee)
    }

    private fun updateBlocking(employee: Employee) =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            statement.executeQuery(
                "UPDATE \"Employees\" SET " +
                        "\"Gender\"='${employee.gender}', " +
                        "\"First_name\"='${employee.name}', " +
                        "\"Last_name\"='${employee.surname}', " +
                        "\"Patronymic\"='${employee.patronymic}', " +
                        "\"Birthdate\"='${employee.birthDate.prepareForSqlStatement()}', " +
                        "\"Profession\"=${employee.profession.id}, " +
                        "\"Salary\"=${employee.salary}, " +
                        "\"Employment_date\"='${employee.birthDate.prepareForSqlStatement()}' " +
                        "WHERE " +
                        "\"Id\"=${employee.id}\n"
            )
        }

    override fun add(employee: Employee): Completable = Completable.fromAction {
        addBlocking(employee)
    }

    private fun addBlocking(employee: Employee) =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            statement.executeQuery(
                "BEGIN " +
                        "\"Add_employee\" (" +
                        "'${employee.gender}', " +
                        "'${employee.name}', " +
                        "'${employee.surname}'," +
                        "'${employee.patronymic}'," +
                        "'${employee.birthDate.prepareForSqlStatement()}'," +
                        "${employee.profession.id}," +
                        "${employee.salary}," +
                        "'${employee.employmentDate.prepareForSqlStatement()}'," +
                        "'null'); " +
                        "END;"
            )
        }


    override fun getById(id: Int): Maybe<Employee> = Maybe.empty()

    override fun removeById(id: Int): Completable =
        Completable.fromAction { removeByIdBlocking(id) }

    private fun removeByIdBlocking(id: Int) =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            statement.executeQuery(
                "DELETE FROM \"Employees\" WHERE \"Id\"=$id"
            )
        }

    override fun fireEmployeeById(id: Int, dismissalDate: Date): Completable =
        Completable.fromAction {
            fireEmployeeByIdBlocking(id, dismissalDate)
        }

    private fun fireEmployeeByIdBlocking(id: Int, dismissalDate: Date) =
        connectionProvider.openConnection().use { connection ->
            val statement = connection.createStatement()
            statement.executeQuery(
                "UPDATE \"Employees\" SET \"Dismissal_date\" = '${dismissalDate.prepareForSqlStatement()}' WHERE \"Id\" = $id"
            )
        }
}