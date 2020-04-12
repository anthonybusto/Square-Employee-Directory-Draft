package com.abusto.square.base_arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


/**
 * @author: Anthony Busto
 * @date:   2020-04-10
 */
abstract class BaseViewModelImpl<I : BaseIntent,
//        A : BaseAction,
//        E : BaseEvent,
        S : BaseViewState,
        R: BaseResult
        > :
        BaseViewModel<I, S>,// BaseViewModel<I, E, S>, //BaseViewModel<I, A, E, S>,
        ViewModel() {

    private val disposables = CompositeDisposable()
    private val intentsSubject: PublishSubject<I> = PublishSubject.create()
//    private val eventsSubject: PublishSubject<E> = PublishSubject.create()
    private val eventsSubject: PublishSubject<BaseEvent> = PublishSubject.create()
    private val statesObservable: Observable<S> by lazy { initStream() }
    private val states: MutableLiveData<S> = MutableLiveData()

//    abstract val reducer : BiFunction<S,R,S>
//    abstract val reducer : BiFunction<S,BaseResult,S>

//    abstract val reducers : Map<KClass<BaseResult>, BiFunction<S,BaseResult,S>>

//    abstract val reducers : Map<Class<*>, Reducer<S, BaseResult>>
    abstract val reducers : Map<Class<*>, Reducer<S, R>>

    override fun listenForIntents(intents: Observable<I>) {
        disposables.add(intents.subscribe(intentsSubject::onNext))
    }

    override fun states(): LiveData<S> {
        disposables.add(statesObservable.subscribe(states::postValue) { it.printStackTrace() })
        return states
    }

//    abstract fun processors(): Array<ProcessorResult<R,E>>

//    abstract fun processors(): Array<BaseProcessor<R,E>>
    abstract fun processors(): Array<BaseProcessor<R>>
//    abstract fun processors(): Array<BaseProcessor<BaseResult>>
//    abstract fun processors(): Array<BaseProcessor>

    abstract fun initialState(): S

    private fun initStream(): Observable<S> {

        val intentToAction = intentsSubject
                .mergeWith(initialIntents())
                .map(::mapIntentToAction)

        val eventToAction = eventsSubject
                .map(::mapEventToAction)

        return intentToAction
                .mergeWith(eventToAction)
//                .compose(processors()[0].actionProcessor)
                .compose { action ->
                    val streams = processors()
                            .map { p -> p.actionProcessor.apply(action) }
                            .toList()

                    Observable.merge(streams)
                }
                /*
                  Using the `scan` operator here allows us to cache each previous state and then
                  pass it to the reducer function. The reducer then can take this previous state
                  and create a new immutable state from the previous cached one and the latest
                  BaseResult that was emitted from our processor layer. This is a nice way to
                  leverage RxJava to cache of ViewStates for us
                 */
                .scan(initialState(), { state, processorResult ->
                    processorResult.events.forEach(eventsSubject::onNext)
                    val reducer= reducers[processorResult.rType]
                    reducer?.reduce?.apply(state, processorResult.result) ?: state

                })
                /*
                  When the reducer emits the exact same previousState again, there is no reason at all to
                  pass this down stream to the view. Actually, it can hurt sometimes where
                  redrawing the UI in cases such as this can cause some `jank` or `glicthy` behavior.
                  This typically can happen when, for example, we are in the middle of showing an animation
                  and we show it twice very quickly, even though nothing changed.
                 */
                .distinctUntilChanged()
                /*
                 Emit the last one event of the stream on subscription.
                 This is useful when a [BaseView[ rebinds to the [BaseViewModel] after rotation.
                 */
                .replay(1)
                /*
                Create the stream on creation without waiting for anyone to subscribe.
                We pass in numberOfSubscribers = 0 so that the connection happens after
                0 subscriptions. This allows the stream to stay alive even when the UI
                disconnects and match the stream's lifecycle to the ViewModel's one. This
                will allow for a better user experience in this application.
                 */
                .autoConnect(0)

    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}