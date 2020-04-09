package com.abusto.square.base_arch

import io.reactivex.Observable

/**
 * Object representing a UI that will
 * a) emit its intents to a view model,
 * b) subscribes to a view model for rendering its UI.
 *
 * @param I The [BaseIntent] that the [BaseView] will be emitting and the [BaseViewModel] will be processing.
 * @param S The [BaseViewState] that the [BaseViewModel] will be reducing and
 * that the [BaseView] will be subscribing to and rendering on screen.
 */
interface BaseView<I : BaseIntent, in S : BaseViewState> {
    /**
     * [Observable] stream used by the [BaseViewModel] to listen to the user's intentions
     * as the user interacts with the [BaseView].
     * All the [BaseView]'s [BaseIntent]s must go through this [Observable]. There will
     * only be ONE exit point out of the [BaseView] and ONE entry point into the [BaseViewModel]
     */
    fun intents(): Observable<I>

    /**
     * Entry point for the [BaseView] to render itself based on a [BaseViewState]. This will be
     * the only ONE entry point into the [BaseView]]
     */
    fun render(state: S)
}