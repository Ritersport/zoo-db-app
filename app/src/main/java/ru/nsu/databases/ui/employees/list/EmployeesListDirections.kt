package ru.nsu.databases.ui.employees.list

import ru.nsu.databases.domain.model.zoo.Employee

sealed interface EmployeesListDirections {

    object ToAddEmployee : EmployeesListDirections
    class ToEmployeeDetails(val employee: Employee) : EmployeesListDirections
}