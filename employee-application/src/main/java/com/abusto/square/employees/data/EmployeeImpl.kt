package com.abusto.square.employees.data

import com.abusto.square.employee_domain.Employee

data class EmployeeImpl(override val uuid: String,
                        override val biography: String,
                        override val emailAddress: String,
                        override val employeeType: String,
                        override val fullName: String,
                        override val phoneNumber: String,
                        override val photoUrlLarge: String,
                        override val photoUrlSmall: String,
                        override val team: String) : Employee


fun Employee.toEmployeeImpl() = EmployeeImpl(
        uuid = uuid,
        biography = biography,
        emailAddress = emailAddress,
        employeeType = employeeType,
        fullName = fullName,
        phoneNumber = phoneNumber,
        photoUrlLarge = photoUrlLarge,
        photoUrlSmall = photoUrlSmall,
        team = team
)