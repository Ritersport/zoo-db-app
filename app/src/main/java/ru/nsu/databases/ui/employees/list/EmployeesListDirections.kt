package ru.nsu.databases.ui.employees.list

import ru.nsu.databases.domain.model.zoo.Employee

sealed interface EmployeesListDirections {

    object ToAddEmployee : EmployeesListDirections
    object ToFilter : EmployeesListDirections
    class ToEmployeeDetails(val employee: Employee) : EmployeesListDirections
}