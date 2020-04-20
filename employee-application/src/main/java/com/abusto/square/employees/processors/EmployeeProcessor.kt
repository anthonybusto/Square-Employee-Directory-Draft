package com.abusto.square.employees.processors

import com.abusto.square.base_arch.*
import com.abusto.square.employees.processors.EmployeeAction.LoadEmployees
import com.abusto.square.employees.processors.EmployeeResult.LoadEmployeesResult
import com.abusto.square.employees.processors.EmployeeAction.OnEmployeeClicked
import com.abusto.square.employee_repo.EmployeeRepository
import com.abusto.square.employees.processors.EmployeeResult.EmployeeClickResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EmployeeProcessor(private val employeeRepo: EmployeeRepository) : BaseProcessor<EmployeeAction, EmployeeResult> {

    override fun canProcess(action: EmployeeAction) = action.javaClass.superclass == EmployeeAction::class.java

    private val loadEmployees = ObservableTransformer<LoadEmployees, LoadEmployeesResult> { actions ->
        actions.flatMap {
            employeeRepo.employees()
                    .map(LoadEmployeesResult::Success)
                    .cast(LoadEmployeesResult::class.java)
                    .onErrorReturn(LoadEmployeesResult::Error)
                    .startWith(LoadEmployeesResult.Loading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }


    private val doOnEmployeeClicked = ObservableTransformer<OnEmployeeClicked, EmployeeClickResult> { actions ->
        actions.flatMap { action ->
            Observable.just(EmployeeClickResult.Success(action.uuid))
        }
    }


    override val actionProcessor = ObservableTransformer<BaseAction, ProcessorResult<EmployeeResult>> { actions ->
        actions.publish { shared ->
            Observable.merge(
                    shared.ofType(LoadEmployees::class.java)
                            .compose(loadEmployees)
                            .cast(EmployeeResult::class.java)
                            .map { ProcessorResult(it) },

                    shared.ofType(OnEmployeeClicked::class.java)
                            .compose(doOnEmployeeClicked)
                            .cast(EmployeeClickResult::class.java)
                            .map { ProcessorResult(it) }

            )
        }
    }
}
