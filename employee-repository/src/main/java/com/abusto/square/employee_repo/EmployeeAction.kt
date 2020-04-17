package com.abusto.square.employee_repo

import com.abusto.square.base_arch.BaseAction

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

sealed class EmployeeAction : BaseAction {
    object LoadEmployees : EmployeeAction()
    data class OnEmployeeClicked(val uuid: String): EmployeeAction()
}