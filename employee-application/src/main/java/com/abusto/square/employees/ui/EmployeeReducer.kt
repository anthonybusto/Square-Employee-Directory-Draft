package com.abusto.square.employees.ui

import com.abusto.square.base_arch.Reducer
import com.abusto.square.base_arch.ReducerResult
import com.abusto.square.employees.processors.EmployeeResult
import com.abusto.square.employees.data.toEmployeeImpl
import io.reactivex.functions.BiFunction


class EmployeeReducer: Reducer<EmployeeDirectoryViewState, EmployeeResult>() {

    override val type: Class<*> = EmployeeResult::class.java

    override val reduce: BiFunction<EmployeeDirectoryViewState, EmployeeResult, ReducerResult<EmployeeDirectoryViewState>>
        get() = BiFunction { previousState: EmployeeDirectoryViewState, result: EmployeeResult ->
            when (result) {
                is EmployeeResult.LoadEmployeesResult.Loading -> ReducerResult(previousState.copy(isLoading = true))
                is EmployeeResult.LoadEmployeesResult.Success -> ReducerResult(previousState.copy(
                        isLoading = false,
                        employees = result.list.map { it.toEmployeeImpl() }
                ))
                is EmployeeResult.LoadEmployeesResult.Error   -> ReducerResult(previousState.copy(isLoading = false))

                //TODO: Implement later if needed
                is EmployeeResult.EmployeeClickResult.Loading -> ReducerResult(previousState.copy())
                is EmployeeResult.EmployeeClickResult.Success -> ReducerResult(previousState.copy())
                is EmployeeResult.EmployeeClickResult.Error   -> ReducerResult(previousState.copy())
            }
        }
}
