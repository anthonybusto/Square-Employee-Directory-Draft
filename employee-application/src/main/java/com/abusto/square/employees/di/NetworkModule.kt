package com.abusto.square.employees.di

import com.abusto.square.employee_api.EmployeeApi
import com.abusto.square.employee_api.EmployeeService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {


//    //OkHttpClient
    single {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    }

    single {
        OkHttpClient().newBuilder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    //Retrofit
    single {
        Retrofit.Builder()
                .baseUrl(get<String>(StringQualifier(QualifierNames.BASE_ENDPOINT)))
                .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
                .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
                .client(get<OkHttpClient>())
                .build()
    }


    factory { RxJava2CallAdapterFactory.create() }

    single(StringQualifier(QualifierNames.BASE_ENDPOINT)) { Endpoints.SQUARE_EMPLOYEE_AWS }

    //GSON
    single { GsonBuilder().create() }

    //API
    factory { EmployeeApi(get<Retrofit>().create(EmployeeService::class.java)) }

}