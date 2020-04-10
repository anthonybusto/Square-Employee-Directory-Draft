package com.abusto.square.base_arch

interface BaseResult


data class ProcessorResult<T: BaseResult, E: BaseEvent>(val result: T, val events: Set<E>)