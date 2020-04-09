package com.abusto.square.employee_model_local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abusto.square.employee_domain.Employee

@Entity(tableName = TABLE_NAME_EMPLOYEE)
data class EmployeeLocal(
    @PrimaryKey(autoGenerate = false)
    override val uuid: String,

    override val biography: String,

    override val emailAddress: String,

    override val employeeType: String,

    override val fullName: String,

    override val phoneNumber: String,

    override val photoUrlLarge: String,

    override val photoUrlSmall: String,

    override val team: String) : Employee


const val TABLE_NAME_EMPLOYEE = "Employee"

fun Employee.toEmployeeLocal() = EmployeeLocal(
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