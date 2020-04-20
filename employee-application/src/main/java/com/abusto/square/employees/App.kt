package com.abusto.square.employees

import android.app.Application
import com.abusto.square.employees.di.appModule
import com.abusto.square.employees.di.dbModule
import com.abusto.square.employees.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule,
                networkModule,
                dbModule))
        }
    }
}