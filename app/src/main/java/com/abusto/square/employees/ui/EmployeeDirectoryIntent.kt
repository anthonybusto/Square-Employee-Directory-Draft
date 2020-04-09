package com.abusto.square.employees.ui

import com.abusto.square.base_arch.BaseIntent


sealed class EmployeeDirectoryIntent: BaseIntent {
    object InitialIntent : EmployeeDirectoryIntent()
}