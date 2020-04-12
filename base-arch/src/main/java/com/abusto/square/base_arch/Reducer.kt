package com.abusto.square.base_arch

import io.reactivex.functions.BiFunction

abstract class Reducer<S: BaseViewState, R: BaseResult> {
    abstract val type: Class<*>
    abstract val reduce : BiFunction<S, R, ReducerResult<S>>
}

data class ReducerResult<S: BaseViewState>(val state: S, val viewEffectSet: Set<ViewEffect<*>> = setOf())