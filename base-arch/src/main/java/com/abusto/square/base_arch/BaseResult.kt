package com.abusto.square.base_arch

import io.reactivex.functions.BiFunction
import kotlin.reflect.KClass

interface BaseResult


//data class ProcessorResult<R: BaseResult, E: BaseEvent>(val result: R, val events: Set<BaseEvent>)

data class ProcessorResult<out R: BaseResult>(val result: R, val events: Set<BaseEvent> = setOf()) {

//    val type = result::class.java
}


//tailrec fun <R: BaseResult> ProcessorResult<R>.type() : Class<*> = run {
//        var type: Class<*>? = result::class.java
//        var prev: Class<*>? = null
//        while(true){
//            if(type?.superclass == null) break
//            prev = type
//            type = type.superclass
//        }
//        if(prev == null) prev = result::class.java
//        return prev
//    }

/**
 * We need to find the parent sealed class type since we are mapping
 * our Processors to Reducers using the BaseResult type for that
 * Processor. The BaseResult is typically a sealed class and should
 * always be implemented like so. The mapping can be set up where
 * we map {ReducerA.type to ReducerA} where ReducerA.type = ResultA.
 * ResultA is a sealed class, containing sub-classes for ResultA.Success,
 * ResultA.Error, ResultA.Loading. The key for the map is ResultA,
 * but the processor spits out ResultA.Loading for example. Because
 * of this mismatch, the reducing mapping will not work as expected
 * since the [BaseViewModelImpl] will try to find the reducer by
 * looking up Processor.Result, which in this example case will
 * be ResultA.Success, but the reducer will be stored in our map
 * at {ReducerA.type to ReducerA}. This is why this extension
 * property logic is important...so we easily find the parent of
 * the sealed class and get a successful mapping response.
 */
val <R: BaseResult> ProcessorResult<R>.rType : Class<*>
  get() {
      var type: Class<*>? = result::class.java
      var prev: Class<*>? = null
      while(true){
          if(type?.superclass == null) break
          prev = type
          type = type.superclass
      }
      return prev ?: result::class.java
  }


//data class ProcessorResult(val result: BaseResult, val events: Set<BaseEvent>) {
//
//    val type: KClass<*> = result::class
//}

abstract class Reducer<S: BaseViewState, R: BaseResult> {

//    abstract val type: KClass<R>

    abstract val type: Class<*>

//    abstract val reduce : BiFunction<S, BaseResult, S>
    abstract val reduce : BiFunction<S, R, S>

}