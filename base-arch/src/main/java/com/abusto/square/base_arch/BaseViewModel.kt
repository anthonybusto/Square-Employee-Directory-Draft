package com.abusto.square.base_arch

import androidx.lifecycle.LiveData
import io.reactivex.Observable


/**
 * Object that will subscribes to a [BaseView]'s [BaseIntent] stream and
 * it will process that stream, reduce it down to a new [BaseViewState] that
 * our view can understand and do something with.
 *
 * @param I The [BaseIntent] that the [BaseViewModel] will be subscribing
 * to.
 * @param S The [BaseViewState] the [BaseViewModel] will be emitting.
 */
//interface BaseViewModel<I : BaseIntent, A: BaseAction, E: BaseEvent, S : BaseViewState> {
//interface BaseViewModel<I : BaseIntent, E: BaseEvent, S : BaseViewState> {

interface BaseViewModel<I : BaseIntent, S : BaseViewState> {


    /**
     * This is our ONE entry point for our
     */
    fun listenForIntents(intents: Observable<I>)
    fun initialIntents(): Observable<I>
    fun states(): LiveData<S>

//    fun mapIntentToAction(intent: I): A
//    fun mapEventToAction(intent: E): A { TODO () }

    fun mapIntentToAction(intent: I): BaseAction
    fun mapEventToAction(intent: BaseEvent): BaseAction { TODO () } //fun mapEventToAction(intent: E): BaseAction { TODO () }
}