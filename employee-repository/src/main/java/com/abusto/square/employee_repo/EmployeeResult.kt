package com.abusto.square.employee_repo

import com.abusto.square.base_arch.BaseResult
import com.abusto.square.employee_domain.Employee



sealed class EmployeeResult: BaseResult {

    sealed class LoadEmployeesResult: EmployeeResult() {
        object Loading: LoadEmployeesResult()
        data class Success(val list: List<Employee>): LoadEmployeesResult()
        data class Error(val throwable: Throwable): LoadEmployeesResult()
    }

    sealed class EmployeeClickResult: EmployeeResult() {
        object Loading: EmployeeClickResult()
        data class Success(val uuid: String): EmployeeClickResult()
        data class Error(val throwable: Throwable): EmployeeClickResult()

    }
}
