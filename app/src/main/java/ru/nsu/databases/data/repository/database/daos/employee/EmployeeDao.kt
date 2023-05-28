package ru.nsu.databases.data.repository.database.daos.employee

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.nsu.databases.domain.model.zoo.Employee
import java.util.Date

interface EmployeeDao {

    fun getAll(): Single<List<Employee>>

    fun addOrUpdate(employee: Employee): Completable

    fun getById(id: Int): Maybe<Employee>

    fun removeById(id: Int): Completable

    fun fireEmployeeById(id: Int, dismissalDate: Date): Completable
}