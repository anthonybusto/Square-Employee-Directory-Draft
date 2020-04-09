package com.abusto.square.employee_repo

import com.abusto.square.base_arch.BaseResult
import com.abusto.square.employee_domain.Employee

///**
// * @author: Anthony Busto
// * @date:   2020-04-08
// */
//sealed class EmployeeResult {
////    data class Success(val list: List<Employee>): EmployeeResult()
////    data class Error(val throwable: Throwable): EmployeeResult()
////    object Loading: EmployeeResult()
//
//
//
//}

sealed class EmployeeResult: BaseResult {

    sealed class LoadEmployeesResult: EmployeeResult() {
        object Loading: LoadEmployeesResult()
        data class Success(val list: List<Employee>): LoadEmployeesResult()
        data class Error(val throwable: Throwable): LoadEmployeesResult()

    }

//    sealed class LoadFiveDayStandardDevResult: WeatherResult() {
//        object Loading: LoadFiveDayStandardDevResult()
//        data class Success(val fiveDayStdDev: FiveDayStdDev): LoadFiveDayStandardDevResult()
//        data class Error(val throwable: Throwable): LoadFiveDayStandardDevResult()
//    }
//
//    data class SwitchModeResult(val mode: TemperatureMode): WeatherResult()
}