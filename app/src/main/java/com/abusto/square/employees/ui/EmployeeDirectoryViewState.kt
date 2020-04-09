package com.abusto.square.employees.ui

import com.abusto.square.base_arch.BaseViewState
import com.abusto.square.employees.data.EmployeeImpl

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */
data class EmployeeDirectoryViewState(val isLoading: Boolean = true,
                                      val employees: List<EmployeeImpl> = emptyList()): BaseViewState