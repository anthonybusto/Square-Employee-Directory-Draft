package com.abusto.square.employees.di

import com.abusto.square.employees.AppDatabase
import com.abusto.square.employee_model_local.EmployeeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {

    // Room Database
    single { AppDatabase.create(androidApplication()) }

    single { EmployeeDatabase(get()) }

    //Employee DAO
    single { get<AppDatabase>().employeeDao() }
}