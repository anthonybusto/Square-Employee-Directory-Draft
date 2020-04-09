package com.abusto.square.employee_api


//import com.abusto.square.employee_model_local.Employee
import com.abusto.square.employee_domain.Employee
import com.google.gson.annotations.SerializedName

data class EmployeeRemote(@SerializedName("uuid")
                          override val uuid: String,
                          @SerializedName("biography")
                          override val biography: String,
                          @SerializedName("email_address")
                          override val emailAddress: String,
                          @SerializedName("employee_type")
                          override  val employeeType: String,
                          @SerializedName("full_name")
                          override val fullName: String,
                          @SerializedName("phone_number")
                          override val phoneNumber: String,
                          @SerializedName("photo_url_large")
                          override val photoUrlLarge: String,
                          @SerializedName("photo_url_small")
                          override val photoUrlSmall: String,
                          @SerializedName("team")
                          override val team: String) : Employee