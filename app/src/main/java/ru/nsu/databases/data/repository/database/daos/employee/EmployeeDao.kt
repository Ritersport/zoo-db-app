package ru.nsu.databases.data.repository.database.daos.employee

import ru.nsu.databases.domain.model.zoo.Employee

interface EmployeeDao {

    fun getAll(): List<Employee>
}