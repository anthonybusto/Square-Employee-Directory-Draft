package com.abusto.square.employee_domain

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */
interface Employee {

    val uuid: String

    val biography: String

    val emailAddress: String

    val employeeType: String

    val fullName: String

    val phoneNumber: String

    val photoUrlLarge: String

    val photoUrlSmall: String

    val team: String
}

