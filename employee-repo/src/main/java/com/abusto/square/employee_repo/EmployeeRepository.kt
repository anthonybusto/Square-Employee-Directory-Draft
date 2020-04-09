package com.abusto.square.employee_repo

import com.abusto.square.employee_api.EmployeeApi
import com.abusto.square.employee_model_local.EmployeeDatabase
import com.abusto.square.employee_domain.Employee
import io.reactivex.Observable


/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

class EmployeeRepository constructor(private val employeeApi: EmployeeApi,
                                     private val employeeDatabase: EmployeeDatabase) {


    fun employees(): Observable<List<Employee>> {
        val db = employeeDatabase.fetchEmployees()
            .toObservable()
        val network = employeeApi.fetchEmployees()
            .map { it.employees }
            //Always write to the database to make sure it always has fresh data
            .doOnSuccess { employeeDatabase.insertEmployees(it) }
            .toObservable()
        return Observable.concatDelayError(listOf(db, network))
            /*
            Pick the first element. We can do this safely since we are always updating the possible
            'stale' data in our database inside [doOnSuccess] and making sure it's 'fresh
             */
            .firstElement()
            .toObservable()
    }
}