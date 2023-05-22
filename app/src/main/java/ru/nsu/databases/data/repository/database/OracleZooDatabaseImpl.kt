package ru.nsu.databases.data.repository.database

import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDaoImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OracleZooDatabaseImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : ZooDatabase {

//    private val databaseConnection: Connection = connectionProvider.openConnection(
//        address = URL.format(IP),
//        credentials.login,
//        credentials.password
//    )

    private val employeeDao: EmployeeDao = EmployeeDaoImpl(
        connectionProvider
    )

    fun employeesDao(): EmployeeDao = employeeDao


}