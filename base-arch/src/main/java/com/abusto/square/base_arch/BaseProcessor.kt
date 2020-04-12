package com.abusto.square.base_arch

import io.reactivex.ObservableTransformer

interface BaseProcessor<in A : BaseAction, R: BaseResult> {
    fun canProcess(action: A): Boolean
    val actionProcessor: ObservableTransformer<BaseAction, ProcessorResult<R>>
}
