package com.abusto.square.employee_repo

import com.abusto.square.base_arch.BaseAction
import java.util.*

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

sealed class EmployeeAction : BaseAction {
    object LoadEmployees : EmployeeAction()
    data class EmployeeClicked(val uuid: String): EmployeeAction()
}