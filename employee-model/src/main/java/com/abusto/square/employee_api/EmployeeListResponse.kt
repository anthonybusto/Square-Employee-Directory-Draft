package com.abusto.square.employee_api


import com.google.gson.annotations.SerializedName

data class EmployeeListResponse(@SerializedName("employees")
                                val employees: List<EmployeeRemote>)