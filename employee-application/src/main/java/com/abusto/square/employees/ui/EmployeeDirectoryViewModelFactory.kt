package com.abusto.square.employees.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.Koin

class EmployeeDirectoryViewModelFactory(private val koin: Koin) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        koin.get<EmployeeDirectoryViewModel>() as T

}


