package com.abusto.square.employees.ui

import com.abusto.square.base_arch.Reducer
import com.abusto.square.employee_repo.EmployeeResult
import com.abusto.square.employees.data.toEmployeeImpl
import io.reactivex.functions.BiFunction


class EmployeeReducer: Reducer<EmployeeDirectoryViewState, EmployeeResult>() {

    override val type: Class<*> = EmployeeResult::class.java// as KClass<*>

    override val reduce: BiFunction<EmployeeDirectoryViewState, EmployeeResult, EmployeeDirectoryViewState>
        get() = BiFunction { previousState: EmployeeDirectoryViewState, result: EmployeeResult ->
            when (result) {
                is EmployeeResult.LoadEmployeesResult.Loading -> previousState.copy(isLoading = true)
                is EmployeeResult.LoadEmployeesResult.Success -> previousState.copy(
                        isLoading = false,
                        employees = result.list.map { it.toEmployeeImpl() }
                )
                is EmployeeResult.LoadEmployeesResult.Error   -> previousState.copy(isLoading = false)

                //TODO: Implement later if needed
                is EmployeeResult.EmployeeClickResult.Loading -> previousState.copy()
                is EmployeeResult.EmployeeClickResult.Success -> previousState.copy()
                is EmployeeResult.EmployeeClickResult.Error   -> previousState.copy()
            }
        }
}
