package ru.nsu.databases.data.repository.database

import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDaoImpl
import javax.inject.Inject
import javax.inject.Singleton


//тупиковая ветвь развития
@Singleton
class OracleZooDatabaseImpl @Inject constructor(
    private val connectionProvider: DatabaseConnectionProvider,
) : ZooDatabase {

    private val employeeDao: EmployeeDao = EmployeeDaoImpl(
        connectionProvider
    )

    fun employeesDao(): EmployeeDao = employeeDao
}