package com.abusto.square.employees.di


import android.content.Context
import com.abusto.square.employee_repo.EmployeeProcessor
import com.abusto.square.employee_repo.EmployeeRepository
import com.abusto.square.employees.ui.EmployeeDirectoryViewModel
import com.abusto.square.employees.utils.PreferenceResolver
//import com.abusto.tweather.utils.PreferenceResolver
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {

    single {
        androidApplication().getSharedPreferences(
            PreferenceResolver.SHARED_PREFS_NAME,
                                                  Context.MODE_PRIVATE)
    }

    single { PreferenceResolver(get()) }

//    factory { EmployeeRepository(get(), get(), get()) }
    factory { EmployeeRepository(get(), get()) }

    factory { EmployeeProcessor(get()) }
//
    factory { EmployeeDirectoryViewModel(get()) }

}