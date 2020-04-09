package com.abusto.square.employee_model_local

import com.abusto.square.employee_domain.Employee
import io.reactivex.Single

class EmployeeDatabase(private val dao: EmployeeDao) {

    fun fetchEmployees(): Single<List<EmployeeLocal>> = dao.fetchEmployees()

//    fun insertEmployee(employee: Employee) = dao.insertEmployee(employee.toEntity())
//
//    fun insertEmployees(employees: List<Employee>) = dao.insertEmployees(employees.map { it.toEntity() })


    fun insertEmployee(employee: Employee) = dao.insertEmployee(employee.toEmployeeLocal())

    fun insertEmployees(employees: List<Employee>) = dao.insertEmployees(employees
        .map { it.toEmployeeLocal() })

}


