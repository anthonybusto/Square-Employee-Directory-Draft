package com.abusto.square.base_arch


abstract class ViewEffect<T> {

    protected abstract val value: T

    private var isConsumed = false

    /**
     * Make sure the ViewEffect only runs one time and doesn't
     * re-trigger on rotation, etc.
     */
    fun consume(consumer: (T) -> Unit) {
        if (!isConsumed) {
            consumer(value)
            isConsumed = true
        }
    }
}