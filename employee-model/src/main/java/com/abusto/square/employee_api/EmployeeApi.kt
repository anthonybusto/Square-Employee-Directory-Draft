package com.abusto.square.employee_api

import io.reactivex.Single

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

class EmployeeApi constructor(private val service: EmployeeService) {

    fun fetchEmployees(): Single<EmployeeListResponse> = service.fetchEmployees()
}




//fun EmployeeRemote.toEntity() = EmployeeLocal(
//        uuid = uuid,
//        biography = biography,
//        emailAddress = emailAddress,
//        employeeType = employeeType,
//        fullName = fullName,
//        phoneNumber = phoneNumber,
//        photoUrlLarge = photoUrlLarge,
//        photoUrlSmall = photoUrlSmall,
//        team = team
//)


