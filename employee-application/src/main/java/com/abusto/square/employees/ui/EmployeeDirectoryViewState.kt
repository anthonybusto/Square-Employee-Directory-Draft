package com.abusto.square.employees.ui

import com.abusto.square.base_arch.BaseViewState
import com.abusto.square.employees.data.EmployeeImpl
import com.xwray.groupie.Group
import com.xwray.groupie.Section

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */
data class EmployeeDirectoryViewState(val isLoading: Boolean = true,
                                      val employees: List<EmployeeImpl> = emptyList()): BaseViewState


fun EmployeeDirectoryViewState.toGroups() : List<Group> = listOf(
    Section().apply { addAll(employees.map(::EmployeeRowItem)) }
)