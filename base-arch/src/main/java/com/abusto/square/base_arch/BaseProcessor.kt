package com.abusto.square.base_arch

import io.reactivex.ObservableTransformer


// PR: ProcessorResult<R, E>
//interface BaseProcessor<A: BaseAction, R: BaseResult, E: BaseEvent> {
//
//    val actionProcessor: ObservableTransformer<A, ProcessorResult<R, E>>
//}


//interface BaseProcessor<R: BaseResult, E: BaseEvent> {

interface BaseProcessor<R: BaseResult> {
//interface BaseProcessor {

//    val actionProcessor: ObservableTransformer<BaseAction, ProcessorResult<R, E>>

    val actionProcessor: ObservableTransformer<BaseAction, ProcessorResult<R>>
//    val actionProcessor: ObservableTransformer<BaseAction, ProcessorResult<BaseResult>>
}


/*
val actionProcessor = ObservableTransformer<EmployeeAction, ProcessorResult<EmployeeResult, EmployeeEvent>> { actions ->
        actions.publish { shared ->
            shared.ofType(LoadEmployees::class.java)
                .compose(loadEmployees).cast(EmployeeResult::class.java)
                .map { ProcessorResult(it, setOf()) }
        }
    }
 */