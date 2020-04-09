package com.abusto.square.employees.di

import com.abusto.square.employee_api.EmployeeApi
import com.abusto.square.employee_api.EmployeeService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {


    //Retrofit
    single {
        Retrofit.Builder()
                .client(get<OkHttpClient>())
                .baseUrl(get<String>(StringQualifier(QualifierNames.BASE_ENDPOINT)))
                .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
                .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
                .build()
    }

    single { OkHttpClient().newBuilder().build() }

    factory { RxJava2CallAdapterFactory.create() }

    single(StringQualifier(QualifierNames.BASE_ENDPOINT)) { Endpoints.SQUARE_EMPLOYEE_AWS }

    //GSON
    single { GsonBuilder().create() }

    //API
    factory { EmployeeApi(get<Retrofit>().create(EmployeeService::class.java)) }

}