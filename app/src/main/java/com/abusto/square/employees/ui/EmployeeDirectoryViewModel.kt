package com.abusto.square.employees.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abusto.square.base_arch.BaseViewModel
import com.abusto.square.employee_repo.*
import com.abusto.square.employees.data.toEmployeeImpl
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.core.Koin

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */


class EmployeeDirectoryViewModel constructor(private val processor: EmployeeProcessor) : ViewModel(),
    BaseViewModel<EmployeeDirectoryIntent, EmployeeAction, EmployeeEvent, EmployeeDirectoryViewState> {

    private val disposables = CompositeDisposable()
    private val intentsSubject: PublishSubject<EmployeeDirectoryIntent> = PublishSubject.create()
    private val eventsSubject: PublishSubject<EmployeeEvent> = PublishSubject.create()
    private val statesObservable: Observable<EmployeeDirectoryViewState> by lazy { initStream() }
    private val states: MutableLiveData<EmployeeDirectoryViewState> = MutableLiveData()

    override fun listenForIntents(intents: Observable<EmployeeDirectoryIntent>) {
        disposables.add(intents.subscribe(intentsSubject::onNext))
    }

    override fun initialIntents(): Observable<EmployeeDirectoryIntent> {
        return Observable.just(EmployeeDirectoryIntent.InitialIntent)
    }

    override fun states(): LiveData<EmployeeDirectoryViewState> {
        disposables.add(statesObservable.subscribe(states::postValue) { it.printStackTrace() })
        return states
    }

//    private fun initStream(): Observable<EmployeeDirectoryViewState> {
//        return intentsSubject
//            .mergeWith(initialIntents())
//            .map(::mapIntentToAction)
//            .compose(processor.actionProcessor)
//            /*
//              Using the `scan` operator here allows us to cache each previous state and then
//              pass it to the reducer function. The reducer then can take this previous state
//              and create a new immutable state from the previous cached one and the latest
//              BaseResult that was emitted from our processor layer. This is a nice way to
//              leverage RxJava to cache of ViewStates for us
//             */
//            .scan(EmployeeDirectoryViewState(), reducer)
//            /*
//              When the reducer emits the exact same previousState again, there is no reason at all to
//              pass this down stream to the view. Actually, it can hurt sometimes where
//              redrawing the UI in cases such as this can cause some `jank` or `glicthy` behavior.
//              This typically can happen when, for example, we are in the middle of showing an animation
//              and we show it twice very quickly, even though nothing changed.
//             */
//            .distinctUntilChanged()
//            /*
//             Emit the last one event of the stream on subscription.
//             This is useful when a [BaseView[ rebinds to the [BaseViewModel] after rotation.
//             */
//            .replay(1)
//            /*
//            Create the stream on creation without waiting for anyone to subscribe.
//            We pass in numberOfSubscribers = 0 so that the connection happens after
//            0 subscriptions. This allows the stream to stay alive even when the UI
//            disconnects and match the stream's lifecycle to the ViewModel's one. This
//            will allow for a better user experience in this application.
//             */
//            .autoConnect(0)
//
//    }


    private fun initStream(): Observable<EmployeeDirectoryViewState> {

        val intentToAction = intentsSubject
            .mergeWith(initialIntents())
            .map(::mapIntentToAction)


        val eventToAction = eventsSubject
            .map(::mapEventToAction)

        return intentToAction
            .mergeWith(eventToAction)
            .compose(processor.actionProcessor)
            /*
              Using the `scan` operator here allows us to cache each previous state and then
              pass it to the reducer function. The reducer then can take this previous state
              and create a new immutable state from the previous cached one and the latest
              BaseResult that was emitted from our processor layer. This is a nice way to
              leverage RxJava to cache of ViewStates for us
             */
            .scan(EmployeeDirectoryViewState(), { state, processorResult ->
                processorResult.events.forEach(eventsSubject::onNext)
                reducer.apply(state, processorResult.result)
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


    /**
     * The reducer's responsibility is simple: Take our to our next state state by using our
     * last state + the incoming result and create a new immutable state that will be pushed
     * down the stream to the view and rendered on screen.
     */

    private val reducer = BiFunction { previousState: EmployeeDirectoryViewState, result: EmployeeResult ->
            when (result) {
                is EmployeeResult.LoadEmployeesResult.Loading -> {
                    previousState.copy(isLoading = true)
                }
                is EmployeeResult.LoadEmployeesResult.Success -> previousState.copy(
                    isLoading = false,
                    employees = result.list.map { it.toEmployeeImpl() }
                )
                is EmployeeResult.LoadEmployeesResult.Error -> previousState.copy(isLoading = false)


            }

        }

//    private val reducer = BiFunction { previousState: WeatherViewState, result: WeatherResult ->
//        when (result) {
//            is WeatherResult.LoadCurrentWeatherResult.Loading -> previousState.copy(isLoading = true)
//            is WeatherResult.LoadCurrentWeatherResult.Success -> previousState.copy(
//                isLoading = false,
//                weather = result.weather,
//                tempMode = result.mode,
//                standDev = result.fiveDayStdDev,
//                icon = if (result.weather.cloudiness > 50) WeatherIcon.CLOUDY_DAY else WeatherIcon.CLEAR_DAY
//            )
//            is WeatherResult.LoadCurrentWeatherResult.Error -> previousState.copy(isLoading = false)
//
//            is WeatherResult.LoadFiveDayStandardDevResult.Loading -> previousState.copy()
//            is WeatherResult.LoadFiveDayStandardDevResult.Success -> previousState.copy(
//                standDev = result.fiveDayStdDev
//            )
//            is WeatherResult.LoadFiveDayStandardDevResult.Error -> previousState.copy()
//
//            is WeatherResult.SwitchModeResult -> previousState.copy(tempMode = result.mode)
//
//        }
//



    /**
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    override fun mapIntentToAction(intent: EmployeeDirectoryIntent): EmployeeAction {
        return when (intent) {
            is EmployeeDirectoryIntent.InitialIntent -> EmployeeAction.LoadEmployees
            is EmployeeDirectoryIntent.EmployeeClicked -> EmployeeAction.EmployeeClicked(intent.uuid)
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}


class EmployeeDirectoryViewModelactory(private val koin: Koin) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        koin.get<EmployeeDirectoryViewModel>() as T

}