package com.abusto.square.employee_repo

import com.abusto.square.employee_api.EmployeeApi
import com.abusto.square.employee_api.EmployeeRemote
import com.abusto.square.employee_model_local.EmployeeDatabase
import com.abusto.square.employee_domain.Employee
import io.reactivex.Observable


/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

class EmployeeRepository constructor(private val employeeApi: EmployeeApi,
                                     private val employeeDatabase: EmployeeDatabase) {


//
//    fun employees(): Observable<List<EmployeeRemote>> {
//        return employeeApi.fetchEmployees()
//            .map { it.employees }
//            .doOnSuccess {
//                employeeDatabase.insertEmployees(it)
//            }
//            //Always write to the database to make sure it always has fresh data
//            .toObservable()
//    }








//    fun employees(): Observable<List<Employee>> {
//        val db = employeeDatabase.fetchEmployees().toObservable()
//        val network = employeeApi.fetchEmployees()
//            .map { it.employees }
//            //Always write to the database to make sure it always has fresh data
//            .doOnSuccess {
//                employeeDatabase.insertEmployees(it)
//            }
//            .toObservable()
//
////        return Observable.concatDelayError(listOf(db, network))
//        return Observable.concatArrayDelayError(db, network)
//            /*
//            Pick the first element. We can do this safely since we are always updating the possible
//            'stale' data in our database inside [doOnSuccess] and making sure it's 'fresh
//             */
//            .firstElement()
//            .toObservable()
//    }





//    fun employees(): Observable<List<Employee>> {
//        val db = employeeDatabase.fetchEmployees().toObservable()
//        val network = employeeApi.fetchEmployees()
//                .map { it.employees }
//                //Always write to the database to make sure it always has fresh data
//            .doOnSuccess {
//                employeeDatabase.insertEmployees(it)
//            }
//                .toObservable()
//
////        return Observable.concatDelayError(listOf(db, network))
////        return Observable.concatArrayEager(db, network.doOnNext {
////            employeeDatabase.insertEmployees(it)
////        })
//
//        return Observable.concatArrayEager(db, network)
//                /*
//                Pick the first element. We can do this safely since we are always updating the possible
//                'stale' data in our database inside [doOnSuccess] and making sure it's 'fresh
//                 */
//                .firstElement()
//                .toObservable()
//    }






    fun employees(): Observable<List<Employee>> {
        val db = employeeDatabase.fetchEmployees().toObservable()
        val network = employeeApi.fetchEmployees()
                .map { it.employees }
                //Always write to the database to make sure it always has fresh data
                .doOnSuccess { employeeDatabase.insertEmployees(it) }
                .toObservable()
        return Observable.mergeDelayError(db, network)
    }


//    fun employees(): Observable<List<Employee>> {
//        val db = employeeDatabase.fetchEmployees().toObservable()
//        val network = employeeApi.fetchEmployees()
//            .map { it.employees }
//            //Always write to the database to make sure it always has fresh data
//            .doOnSuccess { employeeDatabase.insertEmployees(it) }
//            .toObservable()
//        return Observable.concatArrayEagerDelayError(db, network)
//                .firstElement()
//                .toObservable()
//                .switchIfEmpty { employeeApi.fetchEmployees().map { it.employees } }
//    }



}