package com.abusto.square.employee_api

import io.reactivex.Single
import retrofit2.http.GET

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */

interface EmployeeService {

    //https://s3.amazonaws.com/sq-mobile-interview/employees.json
    @GET("employees.json")
    fun fetchEmployees(): Single<EmployeeListResponse>
}

/*
package com.abusto.tweather.api


import com.abusto.tweather.api.data.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {

    @GET("current.json")
    fun fetchCurrentWeather(): Single<WeatherResponse>

    @GET("/future_{day}.json")
    fun fetchFutureWeather(@Path("day") day: Int): Single<WeatherResponse>

}
 */