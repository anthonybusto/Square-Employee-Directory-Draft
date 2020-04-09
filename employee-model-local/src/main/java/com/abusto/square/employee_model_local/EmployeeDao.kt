package com.abusto.square.employee_model_local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun fetchEmployees(): Single<List<EmployeeLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(weather: EmployeeLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(weather: List<EmployeeLocal>): Array<Long>

}