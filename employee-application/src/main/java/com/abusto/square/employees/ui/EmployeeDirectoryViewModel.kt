package com.abusto.square.employees.ui

import com.abusto.square.base_arch.BaseProcessor
import com.abusto.square.base_arch.BaseViewModelImpl
import com.abusto.square.base_arch.Reducer
import com.abusto.square.employee_repo.EmployeeAction
import com.abusto.square.employee_repo.EmployeeProcessor
import com.abusto.square.employee_repo.EmployeeResult
import io.reactivex.Observable


class EmployeeDirectoryViewModel constructor(private val processor: EmployeeProcessor) :
        BaseViewModelImpl<EmployeeDirectoryIntent, EmployeeDirectoryViewState, EmployeeAction, EmployeeResult>() {

    override fun initialIntents(): Observable<EmployeeDirectoryIntent> = Observable.just(EmployeeDirectoryIntent.InitialIntent)

    override fun initialState(): EmployeeDirectoryViewState = EmployeeDirectoryViewState()

    override fun processors(): Array<BaseProcessor<EmployeeAction, EmployeeResult>> = arrayOf(processor)

    override val reducers: EmployeeReducerMap = mutableMapOf<Class<*>, Reducer<EmployeeDirectoryViewState, EmployeeResult>>()
            .apply { EmployeeReducer().also { this[it.type] = it } }
    /**
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    override fun mapIntentToAction(intent: EmployeeDirectoryIntent): EmployeeAction {
        return when (intent) {
            is EmployeeDirectoryIntent.InitialIntent   -> EmployeeAction.LoadEmployees
            is EmployeeDirectoryIntent.EmployeeClicked -> EmployeeAction.OnEmployeeClicked(intent.uuid)
        }
    }
}

typealias EmployeeReducerMap = Map<Class<*>, Reducer<EmployeeDirectoryViewState, EmployeeResult>>
