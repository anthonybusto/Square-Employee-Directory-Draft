package com.abusto.square.employee_repo


import com.abusto.square.base_arch.BaseEvent
import com.abusto.square.base_arch.BaseResult
import com.abusto.square.employee_domain.Employee


sealed class EmployeeEvent : BaseEvent {

}

sealed class EmployeeResult: BaseResult {

    sealed class LoadEmployeesResult: EmployeeResult() {
        object Loading: LoadEmployeesResult()
        data class Success(val list: List<Employee>): LoadEmployeesResult()
        data class Error(val throwable: Throwable): LoadEmployeesResult()

    }
}



//sealed class EmployeeResult<T: BaseResult, E: BaseEvent>: BaseResult {
//
//    sealed class LoadEmployeesResult: EmployeeResult() {
//        object Loading: LoadEmployeesResult()
//        data class Success(val list: List<Employee>): LoadEmployeesResult()
//        data class Error(val throwable: Throwable): LoadEmployeesResult()
//
//    }
//}