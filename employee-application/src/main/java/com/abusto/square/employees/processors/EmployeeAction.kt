package com.abusto.square.employees.processors

import com.abusto.square.base_arch.BaseAction

sealed class EmployeeAction : BaseAction {
    object LoadEmployees : EmployeeAction()
    data class OnEmployeeClicked(val uuid: String): EmployeeAction()
}