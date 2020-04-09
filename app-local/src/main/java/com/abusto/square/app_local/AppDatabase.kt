package com.abusto.square.app_local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abusto.square.employee_model_local.EmployeeDao
import com.abusto.square.employee_model_local.EmployeeLocal
import java.util.concurrent.Executors

/**
 * @author: Anthony Busto
 * @date:   2020-04-08
 */
@Database(entities = [EmployeeLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object {

        private const val DATABASE_NAME = "EmployeeRemote.db"
        private const val THREAD_COUNT = 5
        private val databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_COUNT)

        fun create(context: Context) = synchronized(AppDatabase::class.java) {
            Room.databaseBuilder(context,
                                 AppDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .setQueryExecutor(databaseWriteExecutor)
                    .build()
        }
    }
}
